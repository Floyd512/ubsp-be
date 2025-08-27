package com.udan.ubsp.integration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.ubsp.integration.config.SeaTunnelConfig;
import com.udan.ubsp.integration.entity.SyncTaskExecutionFileEntity;
import com.udan.ubsp.integration.mapper.SyncTaskExecutionFileMapper;
import com.udan.ubsp.integration.mapper.SyncTaskExecutionMapper;
import com.udan.ubsp.integration.service.SyncTaskExecutionFileService;
import com.udan.ubsp.integration.vo.FileItemVO;
import com.udan.ubsp.integration.vo.FileSummaryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SyncTaskExecutionFileServiceImpl extends ServiceImpl<SyncTaskExecutionFileMapper, SyncTaskExecutionFileEntity>
		implements SyncTaskExecutionFileService {

	private final SeaTunnelConfig seaTunnelConfig;
	private final SyncTaskExecutionMapper executionMapper; // reserved for future write operations

	@Override
	public FileSummaryVO summarizeFilesByJobId(String jobId) {
		Path root = Paths.get(seaTunnelConfig.getOutputRoot());
		// 目录即为 outputRoot，在根目录下匹配前缀 T_{jobId}_
		Path dir = root.normalize();
		if (!Files.exists(dir) || !Files.isDirectory(dir)) {
			return new FileSummaryVO(0L, List.of());
		}

		List<FileItemVO> files = new ArrayList<>();
		String prefix = "T_" + jobId + "_";
		try (Stream<Path> s = Files.list(dir)) {
			// 仅根目录，匹配文件名前缀
			s.filter(Files::isRegularFile)
				.filter(p -> p.getFileName().toString().startsWith(prefix))
				.forEach(p -> add(files, root, p));
		} catch (IOException ignored) {
		}

		long totalBytes = files.stream().mapToLong(FileItemVO::getFileSize).sum();
		// 转换为KB（向上取整，避免0KB显示）
		long totalKb = (totalBytes + 1023) / 1024;
		// 同时把每个文件的大小从字节改为KB
		for (int i = 0; i < files.size(); i++) {
			FileItemVO f = files.get(i);
			long kb = (f.getFileSize() + 1023) / 1024;
			files.set(i, new FileItemVO(f.getFileName(), kb));
		}
		return new FileSummaryVO(totalKb, files);
	}

	private void add(List<FileItemVO> files, Path root, Path p) {
		try {
			files.add(new FileItemVO(
					p.getFileName().toString(),
					Files.size(p)
			));
		} catch (IOException ignored) {
		}
	}

	@Override
	public FileSummaryVO syncFilesByJobId(String jobId) {
		// 1) 扫描并返回摘要（files 中 fileSize 为 KB）
		FileSummaryVO summary = summarizeFilesByJobId(jobId);
		// 2) 将扫描结果落库到 ubsp_di_sync_task_execution_file，并回填执行记录的 files_count
		Long jobIdLong = null;
		try { jobIdLong = Long.valueOf(jobId); } catch (Exception ignored) {}
		if (jobIdLong != null && summary.getFiles() != null && !summary.getFiles().isEmpty()) {
			Path root = Paths.get(seaTunnelConfig.getOutputRoot());
			for (FileItemVO f : summary.getFiles()) {
				// 去重：同一个 jobId + fileName 只插一次
				boolean exists = this.lambdaQuery()
					.eq(SyncTaskExecutionFileEntity::getSeaTunnelJobId, jobIdLong)
					.eq(SyncTaskExecutionFileEntity::getFileName, f.getFileName())
					.count() > 0;
				if (!exists) {
					SyncTaskExecutionFileEntity entity = new SyncTaskExecutionFileEntity();
					entity.setSeaTunnelJobId(jobIdLong);
					entity.setFileName(f.getFileName());
					// 重新以字节读取真实文件大小入库
					try {
						long bytes = Files.size(root.resolve(f.getFileName()));
						entity.setFileSize(bytes);
					} catch (IOException e) {
						entity.setFileSize(null);
					}
					this.save(entity);
				}
			}
			// 更新执行记录 files_count
			var exec = executionMapper.selectByJobId(jobId);
			if (exec != null && exec.getId() != null) {
				var update = new com.udan.ubsp.integration.entity.SyncTaskExecutionEntity();
				update.setId(exec.getId());
				executionMapper.updateById(update);
			}
		}
		return summary;
	}
}



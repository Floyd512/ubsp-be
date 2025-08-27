package com.udan.ubsp.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.ubsp.integration.convert.SyncTaskExecutionConvert;
import com.udan.ubsp.integration.dto.FinalizeExecutionDTO;
import com.udan.ubsp.integration.dto.InitExecutionDTO;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.entity.SyncTaskExecutionFileEntity;
import com.udan.ubsp.integration.mapper.SyncTaskExecutionMapper;
import com.udan.ubsp.integration.service.SyncTaskExecutionFileService;
import com.udan.ubsp.integration.service.SyncTaskExecutionService;
import com.udan.ubsp.integration.vo.TaskExecutionDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncTaskExecutionServiceImpl extends ServiceImpl<SyncTaskExecutionMapper, SyncTaskExecutionEntity>
		implements SyncTaskExecutionService {

	private final SyncTaskExecutionConvert convert;
	private final SyncTaskExecutionFileService executionFileService;

	@Override
	public SyncTaskExecutionEntity getByJobId(String jobId) {
		return this.baseMapper.selectByJobId(jobId);
	}

	@Override
	public List<TaskExecutionDetailVO> getExecutionHistoryByTaskId(Long taskId) {
		// 1. 查询该任务的所有执行记录
		LambdaQueryWrapper<SyncTaskExecutionEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SyncTaskExecutionEntity::getTaskId, taskId)
				.orderByDesc(SyncTaskExecutionEntity::getCreateTime);
		
		List<SyncTaskExecutionEntity> executions = this.list(queryWrapper);
		
		// 2. 转换为VO列表
		List<TaskExecutionDetailVO> historyList = executions.stream()
				.map(execution -> {
					TaskExecutionDetailVO detailVO = convert.toDetailVO(execution);
					
					// 3. 查询每个执行记录关联的文件信息
					try {
						Long jobIdLong = Long.valueOf(execution.getSeaTunnelJobId());
						LambdaQueryWrapper<SyncTaskExecutionFileEntity> fileQueryWrapper = new LambdaQueryWrapper<>();
						fileQueryWrapper.eq(SyncTaskExecutionFileEntity::getSeaTunnelJobId, jobIdLong)
								.orderByDesc(SyncTaskExecutionFileEntity::getCreateTime);
						
						List<SyncTaskExecutionFileEntity> fileEntities = executionFileService.list(fileQueryWrapper);
						List<TaskExecutionDetailVO.ExecutionFileVO> fileVOs = convert.toFileVOList(fileEntities);
						detailVO.setFiles(fileVOs);
					} catch (NumberFormatException e) {
						detailVO.setFiles(List.of());
					}
					
					return detailVO;
				})
				.toList();
		
		return historyList;
	}

	@Override
	public Long initExecution(InitExecutionDTO dto) {
		SyncTaskExecutionEntity e = new SyncTaskExecutionEntity();
		e.setTaskId(dto.getTaskId());
		e.setSeaTunnelJobId(dto.getJobId());
		e.setStatus("RUNNING");
		e.setStartTime(LocalDateTime.now());
		this.save(e);
		return e.getId();
	}

	@Override
	public void finalizeExecution(FinalizeExecutionDTO dto) {
		SyncTaskExecutionEntity exist = this.baseMapper.selectByJobId(dto.getSeaTunnelJobId());
		if (exist == null) {
			return;
		}
		SyncTaskExecutionEntity upd = new SyncTaskExecutionEntity();
		upd.setId(exist.getId());
		// 使用 MapStruct 转换器
		convert.updateEntityFromDto(dto, upd);
		this.updateById(upd);
	}
}



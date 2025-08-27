package com.udan.ubsp.integration.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.ubsp.integration.entity.SyncTaskExecutionFileEntity;
import com.udan.ubsp.integration.vo.FileSummaryVO;

public interface SyncTaskExecutionFileService extends IService<SyncTaskExecutionFileEntity> {

	/**
	 * 按 SeaTunnel 作业ID 扫描本地输出目录，返回文件列表与汇总
	 * @param jobId SeaTunnel 作业ID
	 */
	FileSummaryVO summarizeFilesByJobId(String jobId);

	/**
	 * 扫描本地目录并将文件明细与数量回写数据库（插入明细，更新执行记录files_count）
	 * @param jobId SeaTunnel 作业ID
	 */
	FileSummaryVO syncFilesByJobId(String jobId);
}



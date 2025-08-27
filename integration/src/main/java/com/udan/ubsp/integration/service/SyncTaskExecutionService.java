package com.udan.ubsp.integration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.dto.InitExecutionDTO;
import com.udan.ubsp.integration.dto.FinalizeExecutionDTO;
import com.udan.ubsp.integration.vo.TaskExecutionDetailVO;

public interface SyncTaskExecutionService extends IService<SyncTaskExecutionEntity> {

	/**
	 * 根据 SeaTunnel 作业ID 查询执行记录
	 */
	SyncTaskExecutionEntity getByJobId(String jobId);

	/**
	 * 根据任务ID分页查询执行历史记录
	 */
	IPage<TaskExecutionDetailVO> getExecutionHistoryByTaskId(Long taskId, Long current, Long size);

	/**
	 * 初始化执行记录：提交任务时调用，写入PENDING/RUNNING状态的记录
	 */
	Long initExecution(InitExecutionDTO dto);

	/**
	 * 完成执行记录：任务完成后回填统计与状态
	 */
	void finalizeExecution(FinalizeExecutionDTO dto);
}



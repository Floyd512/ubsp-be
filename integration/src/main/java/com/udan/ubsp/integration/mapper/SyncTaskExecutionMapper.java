package com.udan.ubsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SyncTaskExecutionMapper extends BaseMapper<SyncTaskExecutionEntity> {

	/**
	 * 根据 SeaTunnel 作业ID 查询执行记录
	 */
	SyncTaskExecutionEntity selectByJobId(@Param("jobId") String jobId);
}
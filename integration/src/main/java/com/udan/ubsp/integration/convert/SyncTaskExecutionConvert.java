package com.udan.ubsp.integration.convert;

import com.udan.ubsp.integration.dto.FinalizeExecutionDTO;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.entity.SyncTaskExecutionFileEntity;
import com.udan.ubsp.integration.vo.TaskExecutionDetailVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SyncTaskExecutionConvert {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createTime", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "taskId", ignore = true)
	@Mapping(target = "seaTunnelJobId", ignore = true)
	void updateEntityFromDto(FinalizeExecutionDTO dto, @MappingTarget SyncTaskExecutionEntity entity);

	/**
	 * 将执行记录实体转换为详情VO
	 */
	TaskExecutionDetailVO toDetailVO(SyncTaskExecutionEntity entity);

	/**
	 * 将文件实体转换为文件VO
	 */
	TaskExecutionDetailVO.ExecutionFileVO toFileVO(SyncTaskExecutionFileEntity entity);

	/**
	 * 将文件实体列表转换为文件VO列表
	 */
	List<TaskExecutionDetailVO.ExecutionFileVO> toFileVOList(List<SyncTaskExecutionFileEntity> entities);
}
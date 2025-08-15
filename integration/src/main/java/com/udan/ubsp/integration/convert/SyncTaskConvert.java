package com.udan.ubsp.integration.convert;

import com.udan.ubsp.integration.dto.CreateTaskDTO;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SyncTaskConvert {

    SyncTaskEntity toEntity(CreateTaskDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SaveOrUpdateTaskDTO dto, @MappingTarget SyncTaskEntity entity);
}



package com.udan.ubsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SyncTaskMapper extends BaseMapper<SyncTaskEntity> {
    
    /**
     * 根据任务编码查询任务
     * @param taskCode 任务编码
     * @return 任务实体
     */
    SyncTaskEntity selectByTaskCode(@Param("taskCode") String taskCode);
}
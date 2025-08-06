package com.udan.bdsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.bdsp.integration.entity.SyncTaskEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 针对表【udsp_sync_task】的数据库操作Mapper
 * @Author TOM FORD
 * @Date 2025-08-05 17:21:12
 */
@Mapper
public interface SyncTaskMapper extends BaseMapper<SyncTaskEntity> {

}
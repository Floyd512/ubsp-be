package com.udan.bdsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 针对表【bdsp_datasource(数据源表)】的数据库操作Mapper
 * @Author TOM FORD
 * @Date 2025-07-16 13:53:07
 */
@Mapper
public interface SyncDataSourceMapper extends BaseMapper<SyncDataSourceEntity> {

}
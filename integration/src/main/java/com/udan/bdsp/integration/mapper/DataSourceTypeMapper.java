package com.udan.bdsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.bdsp.integration.entity.DataSourceTypeEntity;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description 针对表【ubsp_di_data_source_type】的数据库操作Mapper
 * @Author TOM FORD
 * @Date 2025-01-08 11:08:52
 */
@Mapper
public interface DataSourceTypeMapper extends BaseMapper<DataSourceTypeEntity> {
    
    /**
     * 获取数据源类型支持的节点类型
     */
    @MapKey("id")
    List<Map<String, Object>> selectSupportedNodeTypes();
}
package com.udan.bdsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.bdsp.integration.entity.ConfigTemplateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 数据源配置模版 Mapper 接口
 * @Author TOM FORD
 * @Date 2025-08-07 14:58:17
 */
@Mapper
public interface ConfigTemplateMapper extends BaseMapper<ConfigTemplateEntity> {
    
    /**
     * 根据数据源类型和节点类型查询配置模板
     */
    ConfigTemplateEntity selectByTypeCodeAndNodeType(@Param("typeCode") Integer typeCode, @Param("nodeType") Integer nodeType);
    
    /**
     * 根据数据源类型查询所有配置模板
     */
    List<ConfigTemplateEntity> selectByTypeCode(Integer typeCode);
    
    /**
     * 根据节点类型查询所有配置模板
     */
    List<ConfigTemplateEntity> selectByNodeType(Integer nodeType);
    
    /**
     * 查询所有启用的配置模板
     */
    List<ConfigTemplateEntity> selectEnabledTemplates();
}

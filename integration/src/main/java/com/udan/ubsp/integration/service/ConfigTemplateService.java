package com.udan.ubsp.integration.service;

import com.udan.ubsp.integration.vo.ConfigTemplateVO;

import java.util.List;

/**
 * @Description 配置模板服务接口
 * @Author TOM FORD
 * @Date 2025-08-07
 */
public interface ConfigTemplateService {

    /**
     * 根据数据源类型和节点类型获取配置模板（阶段3：用户拖拽组件时调用）
     */
    ConfigTemplateVO getByTypeCodeAndNodeType(Integer typeCode, Integer nodeType);

    /**
     * 根据数据源类型查询所有配置模板
     */
    List<ConfigTemplateVO> getByTypeCode(Integer typeCode);

    /**
     * 根据节点类型查询所有配置模板
     */
    List<ConfigTemplateVO> getByNodeType(Integer nodeType);

    /**
     * 查询所有启用的配置模板
     */
    List<ConfigTemplateVO> getEnabledTemplates();
}
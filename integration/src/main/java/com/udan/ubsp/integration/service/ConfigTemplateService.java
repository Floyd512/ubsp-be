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
     * 查询所有启用的配置模板
     */
    List<ConfigTemplateVO> getEnabledTemplates();
}
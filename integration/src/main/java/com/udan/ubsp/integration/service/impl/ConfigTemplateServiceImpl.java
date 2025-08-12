package com.udan.ubsp.integration.service.impl;

import com.udan.ubsp.integration.entity.ConfigTemplateEntity;
import com.udan.ubsp.integration.mapper.ConfigTemplateMapper;
import com.udan.ubsp.integration.service.ConfigTemplateService;
import com.udan.ubsp.integration.vo.ConfigTemplateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 配置模板服务实现
 * @Author TOM FORD
 * @Date 2025-08-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigTemplateServiceImpl implements ConfigTemplateService {

    private final ConfigTemplateMapper configTemplateMapper;

    @Override
    public List<ConfigTemplateVO> getEnabledTemplates() {
        List<ConfigTemplateEntity> entities = configTemplateMapper.selectEnabledTemplates();
        return entities.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 将Entity转换为VO
     */
    private ConfigTemplateVO convertToVO(ConfigTemplateEntity entity) {
        ConfigTemplateVO vo = new ConfigTemplateVO();
        BeanUtils.copyProperties(entity, vo);
        // 设置节点类型相关信息
        if (entity.getNodeType() != null) {
            vo.setNodeType(entity.getNodeType().getCode());
            vo.setNodeTypeName(entity.getNodeType().getName());
        }
        // 设置状态
        if (entity.getStatus() != null) {
            vo.setStatus(entity.getStatus().getCode());
        }
        return vo;
    }
}
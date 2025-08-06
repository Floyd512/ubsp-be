package com.udan.bdsp.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udan.bdsp.common.enums.BaseStatusEnum;
import com.udan.bdsp.integration.entity.SyncDataSourceTypeEntity;
import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import com.udan.bdsp.integration.mapper.SyncDataSourceTypeMapper;
import com.udan.bdsp.integration.service.SyncDataSourceTypeService;
import com.udan.bdsp.integration.vo.SyncDataSourceTypeVO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 数据源类型服务实现
 * @Author TOM FORD
 * @Date 2025-08-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SyncDataSourceTypeServiceImpl implements SyncDataSourceTypeService {

    private final SyncDataSourceTypeMapper dataSourceTypeMapper;

    // 缓存数据库配置，key为typeCode
    private Map<Integer, SyncDataSourceTypeEntity> configCache;

    /**
     * 初始化配置缓存
     */
    @PostConstruct
    public void initConfigCache() {
        refreshConfigCache();
    }

    @Override
    public void refreshConfigCache() {
        try {
            LambdaQueryWrapper<SyncDataSourceTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SyncDataSourceTypeEntity::getStatus, BaseStatusEnum.ENABLE);

            List<SyncDataSourceTypeEntity> configs = dataSourceTypeMapper.selectList(queryWrapper);
            configCache = configs.stream()
                    .collect(Collectors.toMap(
                            config -> config.getTypeCode().getCode(),
                            config -> config));
            log.info("数据源配置缓存刷新完成，共加载 {} 个配置", configCache.size());
        } catch (Exception e) {
            log.warn("刷新数据源配置缓存失败，将使用枚举默认配置: {}", e.getMessage());
            configCache = Map.of(); // 空缓存，回退到枚举
        }
    }

    @Override
    public List<SyncDataSourceTypeVO> getAllDataSourceTypes() {
        return Arrays.stream(DataSourceTypeEnum.values())
                .map(this::buildDataSourceTypeVO)
                .collect(Collectors.toList());
    }

    @Override
    public SyncDataSourceTypeVO getDataSourceType(Integer typeCode) {
        DataSourceTypeEnum enumType = DataSourceTypeEnum.getByCode(typeCode);
        if (enumType == null) {
            return null;
        }
        return buildDataSourceTypeVO(enumType);
    }

    @Override
    public boolean isValidDataSourceType(Integer typeCode) {
        return DataSourceTypeEnum.isValidType(typeCode);
    }

    @Override
    public DataSourceTypeEnum getDataSourceTypeEnum(Integer typeCode) {
        return DataSourceTypeEnum.getByCode(typeCode);
    }

    /**
     * 构建数据源类型VO
     */
    private SyncDataSourceTypeVO buildDataSourceTypeVO(DataSourceTypeEnum enumType) {
        SyncDataSourceTypeEntity dbConfig = configCache.get(enumType.getCode());

        SyncDataSourceTypeVO vo = new SyncDataSourceTypeVO();
        vo.setCode(enumType.getCode());
        vo.setName(enumType.getName());
        vo.setCategoryCode(enumType.getCategory().getCode());
        vo.setCategoryName(enumType.getCategory().getName());

        // 优先使用数据库配置，回退到枚举默认值
        if (dbConfig != null) {
            vo.setDefaultPort(
                    dbConfig.getDefaultPort() != null ? dbConfig.getDefaultPort() : enumType.getDefaultPort());
            vo.setRequiresAuth(dbConfig.getRequiresAuth());
            vo.setRequiresDatabase(dbConfig.getRequiresDatabase());
            vo.setConfigTemplate(dbConfig.getConfigTemplate());
            vo.setFieldDefinitions(dbConfig.getFieldDefinitions());
            vo.setStatus(dbConfig.getStatus().getCode());
        }
        return vo;
    }
}
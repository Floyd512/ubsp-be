package com.udan.ubsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.ubsp.integration.entity.ConfigTemplateEntity;
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
     * 查询所有启用的配置模板
     */
    List<ConfigTemplateEntity> selectEnabledTemplates();
}

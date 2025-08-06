package com.udan.bdsp.integration.service;

import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import com.udan.bdsp.integration.vo.SyncDataSourceTypeVO;

import java.util.List;

/**
 * @Description 数据源类型服务接口
 * @Author TOM FORD
 * @Date 2025-08-06
 */
public interface SyncDataSourceTypeService {

    /**
     * 获取所有数据源类型配置
     */
    List<SyncDataSourceTypeVO> getAllDataSourceTypes();

    /**
     * 获取指定数据源类型配置
     */
    SyncDataSourceTypeVO getDataSourceType(Integer typeCode);

    /**
     * 检查数据源类型是否存在
     */
    boolean isValidDataSourceType(Integer typeCode);

    /**
     * 获取数据源类型枚举
     */
    DataSourceTypeEnum getDataSourceTypeEnum(Integer typeCode);

    /**
     * 刷新配置缓存
     */
    void refreshConfigCache();
}
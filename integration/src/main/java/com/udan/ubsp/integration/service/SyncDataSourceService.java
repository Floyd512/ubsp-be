package com.udan.ubsp.integration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.ubsp.integration.dto.SaveOrUpdateDataSourceDTO;
import com.udan.ubsp.integration.dto.SyncDataSourcePageQueryDTO;
import com.udan.ubsp.integration.entity.SyncDataSourceEntity;
import com.udan.ubsp.integration.vo.SyncDataSourceInfoVO;

/**
 * @Description 针对表【ubsp_datasource(数据源表)】的数据库操作Service
 * @Author TOM FORD
 * @Date 2025-07-16 13:44:50
 */
public interface SyncDataSourceService extends IService<SyncDataSourceEntity> {

    IPage<SyncDataSourceInfoVO> pageDataSourceInfo(Page<SyncDataSourceInfoVO> page, SyncDataSourcePageQueryDTO queryDTO);

    void savaOrUpdateDataSource(SaveOrUpdateDataSourceDTO sourceDTO, Long userId);
}
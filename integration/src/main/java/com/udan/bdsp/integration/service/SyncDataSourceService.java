package com.udan.bdsp.integration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.bdsp.integration.dto.SaveOrUpdateDataSourceDTO;
import com.udan.bdsp.integration.dto.SyncDataSourcePageQueryDTO;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import com.udan.bdsp.integration.vo.SyncDataSourceInfoVO;

/**
 * @Description 针对表【bdsp_datasource(数据源表)】的数据库操作Service
 * @Author TOM FORD
 * @Date 2025-07-16 13:44:50
 */
public interface SyncDataSourceService extends IService<SyncDataSourceEntity> {

    IPage<SyncDataSourceInfoVO> pageDataSourceInfo(Page<SyncDataSourceInfoVO> page, SyncDataSourcePageQueryDTO queryDTO);

    void savaOrUpdateDataSource(SaveOrUpdateDataSourceDTO sourceDTO, Long userId);
}
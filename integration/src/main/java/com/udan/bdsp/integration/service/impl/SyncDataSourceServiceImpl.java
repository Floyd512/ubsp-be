package com.udan.bdsp.integration.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import com.udan.bdsp.integration.mapper.SyncDataSourceMapper;
import com.udan.bdsp.integration.service.SyncDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description 针对表【bdsp_datasource(数据源表)】的数据库操作Service实现
 * @Author TOM FORD
 * @Date 2025-07-16 13:46:39
 */
@Service
public class SyncDataSourceServiceImpl extends ServiceImpl<SyncDataSourceMapper, SyncDataSourceEntity> implements SyncDataSourceService {

    @Autowired
    private SyncDataSourceMapper sourceMapper;
}
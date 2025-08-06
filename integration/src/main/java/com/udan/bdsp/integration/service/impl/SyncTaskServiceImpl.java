package com.udan.bdsp.integration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import com.udan.bdsp.integration.mapper.SyncDataSourceMapper;
import com.udan.bdsp.integration.mapper.SyncTaskMapper;
import com.udan.bdsp.integration.service.SyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 针对表【bdsp_sync_task】的数据库操作 Service 实现
 * @Author TOM FORD
 * @Date 2025-08-05 17:15:44
 */
@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncDataSourceMapper, SyncDataSourceEntity> implements SyncTaskService {

    @Autowired
    private SyncTaskMapper taskMapper;
}

package com.udan.ubsp.integration.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.ubsp.integration.entity.SyncDataSourceEntity;
import com.udan.ubsp.integration.mapper.SyncDataSourceMapper;
import com.udan.ubsp.integration.mapper.SyncTaskMapper;
import com.udan.ubsp.integration.service.SyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 针对表【ubsp_sync_task】的数据库操作 Service 实现
 * @Author TOM FORD
 * @Date 2025-08-05 17:15:44
 */
@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncDataSourceMapper, SyncDataSourceEntity> implements SyncTaskService {

    @Autowired
    private SyncTaskMapper taskMapper;
}

package com.udan.ubsp.integration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.vo.TaskExecutionVO;

public interface SyncTaskService extends IService<SyncTaskEntity> {

    /**
     * 保存或更新任务
     * @param dto 任务数据传输对象
     * @return 任务ID
     */
    Long saveOrUpdateTask(SaveOrUpdateTaskDTO dto);

    /**
     * 分页查询任务
     */
    IPage<SyncTaskEntity> pageTasks(Page<SyncTaskEntity> page, String keyword, Integer status, String sourceType, String sinkType);

    /**
     * 执行任务
     * @param taskId 任务ID
     * @return 执行结果
     */
    TaskExecutionVO executeTask(Long taskId);
}
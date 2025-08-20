package com.udan.ubsp.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.exception.UBSPException;
import com.udan.ubsp.integration.convert.SyncTaskConvert;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.mapper.SyncTaskMapper;
import com.udan.ubsp.integration.service.SyncTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTaskEntity> implements SyncTaskService {

    private final SyncTaskConvert convert;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrUpdateTask(SaveOrUpdateTaskDTO dto) {
        SyncTaskEntity task;

        if (dto.getId() != null) {
            // 更新任务 - 先查询现有任务
            task = this.getById(dto.getId());
            if (task == null) {
                throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);
            }

            // 保存原有的taskCode，避免被覆盖
            String originalTaskCode = task.getTaskCode();
            convert.updateEntityFromDto(dto, task);
            task.setTaskCode(originalTaskCode); // 恢复原有taskCode
        } else {
            // 创建新任务
            task = new SyncTaskEntity();
            convert.updateEntityFromDto(dto, task);

            // 设置默认值
            if (task.getStatus() == null) {
                task.setStatus(1); // 默认启用
            }

            // 生成UUID任务编码
            task.setTaskCode(generateTaskCode());
        }

        // 使用MyBatis Plus的saveOrUpdate方法
        this.saveOrUpdate(task);
        return task.getId();
    }

    @Override
    public IPage<SyncTaskEntity> pageTasks(Page<SyncTaskEntity> page, String keyword, Integer status, String sourceType,
            String sinkType) {
        LambdaQueryWrapper<SyncTaskEntity> qw = new LambdaQueryWrapper<>();
        qw.like(keyword != null && !keyword.isEmpty(), SyncTaskEntity::getTaskName, keyword)
                .eq(status != null, SyncTaskEntity::getStatus, status)
                .eq(sourceType != null && !sourceType.isEmpty(), SyncTaskEntity::getSourceType, sourceType)
                .eq(sinkType != null && !sinkType.isEmpty(), SyncTaskEntity::getSinkType, sinkType)
                .orderByDesc(SyncTaskEntity::getUpdateTime);
        return this.page(page, qw);
    }

    /**
     * 生成UUID任务编码
     */
    private String generateTaskCode() {
        return "TASK-" + UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 16);
    }
}
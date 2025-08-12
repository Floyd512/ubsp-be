package com.udan.ubsp.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udan.ubsp.common.exception.LeaseException;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.entity.SyncTaskVersionEntity;
import com.udan.ubsp.integration.mapper.SyncTaskExecutionMapper;
import com.udan.ubsp.integration.mapper.SyncTaskMapper;
import com.udan.ubsp.integration.mapper.SyncTaskVersionMapper;
import com.udan.ubsp.integration.service.SyncTaskService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTaskEntity> implements SyncTaskService {

    // @Autowired
    // private SyncTaskMapper taskMapper; // 预留按需使用
    @Autowired
    private SyncTaskVersionMapper versionMapper;
    @Autowired
    private SyncTaskExecutionMapper executionMapper;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(SyncTaskEntity task,
                           JsonNode envJson,
                           JsonNode sourceJson,
                           JsonNode transformsJson,
                           JsonNode sinkJson,
                           String renderedText,
                           JsonNode renderedJson,
                           String changeMemo) {
        task.setCurrentVersionNo(1);
        if (task.getStatus() == null) {
            task.setStatus("ACTIVE");
        }
        this.save(task);

        SyncTaskVersionEntity version = new SyncTaskVersionEntity();
        version.setTaskId(task.getId());
        version.setVersionNo(1);
        version.setSeaTunnelVersion("2.3.11");
        version.setConfigFormat("JSON");
        version.setEnvJson(envJson);
        version.setSourceJson(sourceJson);
        version.setTransformsJson(transformsJson);
        version.setSinkJson(sinkJson);
        version.setRenderedConfigText(renderedText);
        version.setRenderedConfigJson(renderedJson);
        version.setChangeMemo(changeMemo);
        String hash = DigestUtils.md5Hex((safeString(envJson) + safeString(sourceJson) + safeString(transformsJson) + safeString(sinkJson)).getBytes(StandardCharsets.UTF_8));
        version.setConfigHash(hash);
        versionMapper.insert(version);
        return task.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createNewVersion(Long taskId,
                                    JsonNode envJson,
                                    JsonNode sourceJson,
                                    JsonNode transformsJson,
                                    JsonNode sinkJson,
                                    String renderedText,
                                    JsonNode renderedJson,
                                    boolean switchToCurrent,
                                    String changeMemo) {
        SyncTaskEntity task = this.getById(taskId);
        if (task == null) {
            throw new LeaseException(ResultCodeEnum.COMMON_FAIL);
        }
        int nextVersion = (task.getCurrentVersionNo() == null ? 0 : task.getCurrentVersionNo()) + 1;
        SyncTaskVersionEntity version = new SyncTaskVersionEntity();
        version.setTaskId(taskId);
        version.setVersionNo(nextVersion);
        version.setSeaTunnelVersion("2.3.11");
        version.setConfigFormat("JSON");
        version.setEnvJson(envJson);
        version.setSourceJson(sourceJson);
        version.setTransformsJson(transformsJson);
        version.setSinkJson(sinkJson);
        version.setRenderedConfigText(renderedText);
        version.setRenderedConfigJson(renderedJson);
        version.setChangeMemo(changeMemo);
        String hash = DigestUtils.md5Hex((safeString(envJson) + safeString(sourceJson) + safeString(transformsJson) + safeString(sinkJson)).getBytes(StandardCharsets.UTF_8));
        version.setConfigHash(hash);
        versionMapper.insert(version);

        if (switchToCurrent) {
            task.setCurrentVersionNo(nextVersion);
            this.updateById(task);
        }
        return nextVersion;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void switchVersion(Long taskId, Integer versionNo) {
        SyncTaskEntity task = this.getById(taskId);
        if (task == null) {
            throw new LeaseException(ResultCodeEnum.COMMON_FAIL);
        }
        LambdaQueryWrapper<SyncTaskVersionEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(SyncTaskVersionEntity::getTaskId, taskId).eq(SyncTaskVersionEntity::getVersionNo, versionNo);
        SyncTaskVersionEntity version = versionMapper.selectOne(qw);
        if (version == null) {
            throw new LeaseException(ResultCodeEnum.COMMON_FAIL);
        }
        task.setCurrentVersionNo(versionNo);
        this.updateById(task);
    }

    @Override
    public String renderConfigText(JsonNode envJson, JsonNode sourceJson, JsonNode transformsJson, JsonNode sinkJson, String format) {
        // 简单拼装 JSON（HOCON 可后续扩展单独渲染器）
        try {
            var root = OBJECT_MAPPER.createObjectNode();
            if (envJson != null) root.set("env", envJson);
            if (sourceJson != null) root.set("source", sourceJson);
            if (transformsJson != null) root.set("transform", transformsJson);
            if (sinkJson != null) root.set("sink", sinkJson);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        } catch (Exception e) {
            throw new LeaseException(ResultCodeEnum.COMMON_FAIL);
        }
    }

    @Override
    public IPage<SyncTaskEntity> pageTasks(Page<SyncTaskEntity> page, String keyword, String status, String sourceType, String sinkType) {
        LambdaQueryWrapper<SyncTaskEntity> qw = new LambdaQueryWrapper<>();
        qw.like(keyword != null && !keyword.isEmpty(), SyncTaskEntity::getTaskName, keyword)
          .eq(status != null && !status.isEmpty(), SyncTaskEntity::getStatus, status)
          .eq(sourceType != null && !sourceType.isEmpty(), SyncTaskEntity::getSourceType, sourceType)
          .eq(sinkType != null && !sinkType.isEmpty(), SyncTaskEntity::getSinkType, sinkType)
          .orderByDesc(SyncTaskEntity::getUpdateTime);
        return this.page(page, qw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long triggerExecution(Long taskId, String triggerType) {
        SyncTaskEntity task = this.getById(taskId);
        if (task == null) {
            throw new LeaseException(ResultCodeEnum.COMMON_FAIL);
        }
        SyncTaskExecutionEntity exec = new SyncTaskExecutionEntity();
        exec.setTaskId(taskId);
        exec.setVersionNo(task.getCurrentVersionNo());
        exec.setTriggerType(triggerType);
        exec.setStatus("PENDING");
        exec.setStartTime(new Date());
        executionMapper.insert(exec);
        return exec.getId();
    }

    @Override
    public SyncTaskVersionEntity getVersion(Long taskId, Integer versionNo) {
        LambdaQueryWrapper<SyncTaskVersionEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(SyncTaskVersionEntity::getTaskId, taskId).eq(SyncTaskVersionEntity::getVersionNo, versionNo);
        return versionMapper.selectOne(qw);
    }

    @Override
    public SyncTaskExecutionEntity getExecution(Long executionId) {
        return executionMapper.selectById(executionId);
    }

    private String safeString(JsonNode node) {
        return node == null ? "" : node.toString();
    }
}

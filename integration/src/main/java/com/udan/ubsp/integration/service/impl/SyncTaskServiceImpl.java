package com.udan.ubsp.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.exception.UBSPException;
import com.udan.ubsp.integration.convert.SyncTaskConvert;
import com.udan.ubsp.integration.dto.CreateTaskDTO;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.dto.UpdateVersionDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.entity.SyncTaskVersionEntity;
import com.udan.ubsp.integration.mapper.SyncTaskExecutionMapper;
import com.udan.ubsp.integration.mapper.SyncTaskMapper;
import com.udan.ubsp.integration.mapper.SyncTaskVersionMapper;
import com.udan.ubsp.integration.service.SyncTaskService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTaskEntity> implements SyncTaskService {

    // private final SyncTaskMapper taskMapper;

    private final SyncTaskVersionMapper versionMapper;

    private final SyncTaskExecutionMapper executionMapper;

    private final SyncTaskConvert convert;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(CreateTaskDTO dto) {
        SyncTaskEntity task = convert.toEntity(dto);
        return createTask(task, dto.getEnv(), dto.getSource(), dto.getTransforms(), dto.getSink(),
                dto.getRenderedText(), dto.getRenderedJson(), dto.getChangeMemo());
    }

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
            task.setStatus("DRAFT");
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
    public Integer createNewVersion(UpdateVersionDTO dto) {
        Long taskId = dto.getTaskId();
        JsonNode envJson = dto.getEnv();
        JsonNode sourceJson = dto.getSource();
        JsonNode transformsJson = dto.getTransforms();
        JsonNode sinkJson = dto.getSink();
        String renderedText = dto.getRenderedText();
        JsonNode renderedJson = dto.getRenderedJson();
        boolean switchToCurrent = Boolean.TRUE.equals(dto.getSwitchToCurrent());
        String changeMemo = dto.getChangeMemo();
        
        SyncTaskEntity task = this.getById(taskId);
        if (task == null) {
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);
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
    public Integer createNewVersion(Long taskId,
                                    JsonNode envJson,
                                    JsonNode sourceJson,
                                    JsonNode transformsJson,
                                    JsonNode sinkJson,
                                    String renderedText,
                                    JsonNode renderedJson,
                                    boolean switchToCurrent,
                                    String changeMemo) {
        UpdateVersionDTO dto = new UpdateVersionDTO();
        dto.setTaskId(taskId);
        dto.setEnv(envJson);
        dto.setSource(sourceJson);
        dto.setTransforms(transformsJson);
        dto.setSink(sinkJson);
        dto.setRenderedText(renderedText);
        dto.setRenderedJson(renderedJson);
        dto.setSwitchToCurrent(switchToCurrent);
        dto.setChangeMemo(changeMemo);
        return createNewVersion(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void switchVersion(Long taskId, Integer versionNo) {
        SyncTaskEntity task = this.getById(taskId);
        if (task == null) {
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);
        }
        LambdaQueryWrapper<SyncTaskVersionEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(SyncTaskVersionEntity::getTaskId, taskId).eq(SyncTaskVersionEntity::getVersionNo, versionNo);
        SyncTaskVersionEntity version = versionMapper.selectOne(qw);
        if (version == null) {
            throw new UBSPException(ResultCodeEnum.INTEGRATION_VERSION_NOT_FOUND);
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
            throw new UBSPException(ResultCodeEnum.INTEGRATION_CONFIG_RENDER_FAILED);
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
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrUpdateTask(SaveOrUpdateTaskDTO dto) {
        if (dto.getId() != null) {
            // 更新任务
            return updateTask(dto);
        } else {
            // 创建新任务
            return createTaskFromDTO(dto);
        }
    }

    /**
     * 创建新任务
     */
    private Long createTaskFromDTO(SaveOrUpdateTaskDTO dto) {
        SyncTaskEntity task = new SyncTaskEntity();
        convert.updateEntityFromDto(dto, task);

        // 设置默认值
        task.setCurrentVersionNo(1);
        if (task.getStatus() == null) {
            task.setStatus("DRAFT");
        }

        // 如果没有提供任务编码，生成一个
        if (task.getTaskCode() == null || task.getTaskCode().trim().isEmpty()) {
            task.setTaskCode(generateTaskCode());
        }

        // 检查任务编码是否重复
        if (this.baseMapper.selectByTaskCode(task.getTaskCode()) != null) {
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_CODE_EXISTS);
        }

        return createTask(task, dto.getEnv(), dto.getSource(), dto.getTransforms(),
                dto.getSink(), dto.getRenderedText(), dto.getRenderedJson(),
                dto.getChangeMemo());
    }

    /**
     * 更新任务
     */
    private Long updateTask(SaveOrUpdateTaskDTO dto) {
        SyncTaskEntity existingTask = this.getById(dto.getId());
        if (existingTask == null) {
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);
        }

        // 更新任务基本信息
        convert.updateEntityFromDto(dto, existingTask);
        this.updateById(existingTask);

        // 创建新版本
        boolean switchToCurrent = dto.getSwitchToCurrent() != null ? dto.getSwitchToCurrent() : true;
        createNewVersion(dto.getId(), dto.getEnv(), dto.getSource(), dto.getTransforms(),
                dto.getSink(), dto.getRenderedText(), dto.getRenderedJson(),
                switchToCurrent, dto.getChangeMemo());

        return dto.getId();
    }


    /**
     * 将DTO数据复制到Entity
     */
    // MapStruct 已替代手动 copy

    /**
     * 生成任务编码
     */
    private String generateTaskCode() {
        return "TASK_" + System.currentTimeMillis() + "_" +
                Integer.toHexString((int) (Math.random() * 0xFFFF)).toUpperCase();
    }

    private String safeString(JsonNode node) {
        return node == null ? "" : node.toString();
    }
}

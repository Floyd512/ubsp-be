package com.udan.ubsp.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.exception.UBSPException;
import com.udan.ubsp.integration.convert.SyncTaskConvert;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.dto.SeaTunnelJobSubmitDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.mapper.SyncTaskMapper;
import com.udan.ubsp.integration.service.SeaTunnelApiService;
import com.udan.ubsp.integration.service.SyncTaskService;
import com.udan.ubsp.integration.vo.TaskExecutionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import com.udan.ubsp.system.entity.SystemUserEntity;
import com.udan.ubsp.system.service.SystemUserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTaskEntity> implements SyncTaskService {

    private final SyncTaskConvert convert;
    private final SeaTunnelApiService seaTunnelApiService;
    private final ObjectMapper objectMapper;
    private final SystemUserService systemUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrUpdateTask(SaveOrUpdateTaskDTO dto, Long operatorUserId) {
        SyncTaskEntity task;

        if (dto.getId() != null) {
            // 更新任务 - 先查询现有任务
            task = this.getById(dto.getId());
            if (task == null) {
                throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);
            }

            // MapStruct 已忽略 taskCode 映射，这里直接更新其它字段
            convert.updateEntityFromDto(dto, task);
            // 记录更新人
            task.setUpdateUserId(operatorUserId);
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
            // 记录创建/更新人
            task.setCreateUserId(operatorUserId);
        }

        // 清理配置JSON，移除未填写的占位符和空值
        cleanConfigurationJson(task);

        // 避免额外一次存在性查询：根据是否有ID决定 insert 或 update
        if (dto.getId() == null) {
            this.save(task);
        } else {
            this.updateById(task);
        }
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
        IPage<SyncTaskEntity> result = this.page(page, qw);

        // 追加创建人/更新人姓名
        List<SyncTaskEntity> records = result.getRecords();
        if (records != null && !records.isEmpty()) {
            Set<Long> userIds = records.stream()
                    .flatMap(t -> java.util.stream.Stream.of(t.getCreateUserId(), t.getUpdateUserId()))
                    .filter(id -> id != null && id > 0)
                    .collect(Collectors.toSet());
            if (!userIds.isEmpty()) {
                List<SystemUserEntity> users = systemUserService.listByIds(userIds);
                Map<Long, String> idToName = new HashMap<>();
                if (users != null) {
                    for (SystemUserEntity u : users) {
                        idToName.put(u.getId(), u.getRealName());
                    }
                }
                for (SyncTaskEntity t : records) {
                    if (t.getCreateUserId() != null) {
                        t.setCreateUserName(idToName.get(t.getCreateUserId()));
                    }
                    if (t.getUpdateUserId() != null) {
                        t.setUpdateUserName(idToName.get(t.getUpdateUserId()));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public TaskExecutionVO executeTask(Long taskId) {
        log.info("开始执行任务，任务ID: {}", taskId);
        
        // 1. 查询任务信息
        SyncTaskEntity task = this.getById(taskId);
        
        // 检查任务状态
        if (task.getStatus() != 1) {
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, "任务未启用，无法执行");
        }
        
        try {
            // 2. 组装SeaTunnel配置JSON
            Map<String, Object> seaTunnelConfig = buildSeaTunnelConfig(task);
            
            // 3. 构建提交DTO
            SeaTunnelJobSubmitDTO submitDTO = new SeaTunnelJobSubmitDTO();
            submitDTO.setJobName(task.getTaskName());
            submitDTO.setFormat("JSON");
            submitDTO.setIsStartWithSavePoint(false);
            submitDTO.setConfig(seaTunnelConfig);
            
            log.info("提交任务到SeaTunnel，任务名称: {}, 配置: {}", task.getTaskName(), seaTunnelConfig);
            
            // 4. 调用SeaTunnel API
            TaskExecutionVO result = seaTunnelApiService.submitJob(submitDTO);
            
            log.info("任务提交成功，任务ID: {}, SeaTunnel作业ID: {}", taskId, result.getJobId());
            
            return result;
            
        } catch (Exception e) {
            log.error("提交任务失败，任务ID: {}", taskId, e);
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                  "任务提交失败: " + e.getMessage());
        }
    }

    /**
     * 组装SeaTunnel配置JSON
     * @param task 任务实体
     * @return SeaTunnel配置
     */
    private Map<String, Object> buildSeaTunnelConfig(SyncTaskEntity task) {
        try {
            // 使用LinkedHashMap保证顺序：env -> source -> sink
            Map<String, Object> config = new LinkedHashMap<>();
            
            // 1. 组装env配置 (必须在最前面)
            if (task.getEnvJson() != null) {
                Map<String, Object> envConfig = objectMapper.convertValue(task.getEnvJson(), 
                        new TypeReference<Map<String, Object>>() {});
                config.put("env", envConfig.getOrDefault("env", envConfig));
            }
            
            // 2. 组装source配置 (在中间)
            if (task.getSourceJson() != null) {
                Map<String, Object> sourceConfig = objectMapper.convertValue(task.getSourceJson(), 
                        new TypeReference<Map<String, Object>>() {});
                config.put("source", sourceConfig.getOrDefault("source", sourceConfig));
            }
            
            // 3. 组装transform配置（可选，在source和sink之间）
            if (task.getTransformJson() != null && !task.getTransformJson().isEmpty()) {
                Map<String, Object> transformConfig = objectMapper.convertValue(task.getTransformJson(), 
                        new TypeReference<Map<String, Object>>() {});
                config.put("transform", transformConfig.getOrDefault("transform", transformConfig));
            }
            
            // 4. 组装sink配置 (在最后面)
            if (task.getSinkJson() != null) {
                Map<String, Object> sinkConfig = objectMapper.convertValue(task.getSinkJson(), 
                        new TypeReference<Map<String, Object>>() {});
                config.put("sink", sinkConfig.getOrDefault("sink", sinkConfig));
            }
            
            // 输出格式化的JSON用于调试
            String configJson = objectMapper.writeValueAsString(config);
            log.info("组装的SeaTunnel配置JSON: {}", configJson);
            
            return config;
            
        } catch (Exception e) {
            log.error("组装SeaTunnel配置失败，任务ID: {}", task.getId(), e);
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                  "组装配置失败: " + e.getMessage());
        }
    }

    /**
     * 清理配置JSON，移除未填写的占位符和空值
     * @param task 任务实体
     */
    private void cleanConfigurationJson(SyncTaskEntity task) {
        try {
            // 清理环境配置JSON
            if (task.getEnvJson() != null) {
                task.setEnvJson(cleanJsonNode(task.getEnvJson()));
            }
            
            // 清理源端配置JSON
            if (task.getSourceJson() != null) {
                task.setSourceJson(cleanJsonNode(task.getSourceJson()));
            }
            
            // 清理转换配置JSON
            if (task.getTransformJson() != null) {
                task.setTransformJson(cleanJsonNode(task.getTransformJson()));
            }
            
            // 清理目标端配置JSON
            if (task.getSinkJson() != null) {
                task.setSinkJson(cleanJsonNode(task.getSinkJson()));
            }
            
            log.debug("配置JSON清理完成");
            
        } catch (Exception e) {
            log.error("清理配置JSON时发生异常", e);
            // 不抛出异常，允许保存原始数据
        }
    }
    
    /**
     * 清理JsonNode，移除占位符和空值
     * @param node 原始JsonNode
     * @return 清理后的JsonNode
     */
    private com.fasterxml.jackson.databind.JsonNode cleanJsonNode(com.fasterxml.jackson.databind.JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        
        try {
            // 将JsonNode转换为Map进行处理
            Map<String, Object> map = objectMapper.convertValue(node, 
                    new TypeReference<Map<String, Object>>() {});
            
            // 递归清理Map
            Map<String, Object> cleanedMap = cleanMap(map);
            
            // 如果清理后为空，返回null
            if (cleanedMap == null || cleanedMap.isEmpty()) {
                return null;
            }
            
            // 转换回JsonNode
            return objectMapper.valueToTree(cleanedMap);
            
        } catch (Exception e) {
            log.warn("清理JsonNode时发生异常，保留原始数据", e);
            return node;
        }
    }
    
    /**
     * 递归清理Map，移除占位符和空值
     * @param map 原始Map
     * @return 清理后的Map
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> cleanMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        
        Map<String, Object> result = new LinkedHashMap<>();
        
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            // 跳过null值
            if (value == null) {
                continue;
            }
            
            // 处理字符串值
            if (value instanceof String strValue) {
                // 跳过空字符串和占位符
                if (strValue.trim().isEmpty() || isPlaceholder(strValue)) {
                    log.debug("移除占位符或空值字段: {} = {}", key, strValue);
                    continue;
                }
                result.put(key, strValue);
            }
            // 处理嵌套Map
            else if (value instanceof Map) {
                Map<String, Object> cleanedSubMap = cleanMap((Map<String, Object>) value);
                if (cleanedSubMap != null && !cleanedSubMap.isEmpty()) {
                    result.put(key, cleanedSubMap);
                }
            }
            // 处理List
            else if (value instanceof java.util.List) {
                java.util.List<Object> list = (java.util.List<Object>) value;
                java.util.List<Object> cleanedList = new java.util.ArrayList<>();
                
                for (Object item : list) {
                    if (item instanceof Map) {
                        Map<String, Object> cleanedItem = cleanMap((Map<String, Object>) item);
                        if (cleanedItem != null && !cleanedItem.isEmpty()) {
                            cleanedList.add(cleanedItem);
                        }
                    } else if (item instanceof String strItem) {
                        if (!strItem.trim().isEmpty() && !isPlaceholder(strItem)) {
                            cleanedList.add(strItem);
                        }
                    } else if (item != null) {
                        cleanedList.add(item);
                    }
                }
                
                if (!cleanedList.isEmpty()) {
                    result.put(key, cleanedList);
                }
            }
            // 处理其他类型（数字、布尔等）
            else {
                result.put(key, value);
            }
        }
        
        return result.isEmpty() ? null : result;
    }
    
    /**
     * 判断字符串是否为占位符
     * @param value 字符串值
     * @return 是否为占位符
     */
    private boolean isPlaceholder(String value) {
        if (value == null) {
            return false;
        }
        
        String trimmed = value.trim();
        // 检查是否为 ${...} 格式的占位符
        return trimmed.startsWith("${") && trimmed.endsWith("}");
    }

    /**
     * 生成UUID任务编码
     */
    private String generateTaskCode() {
        return "TASK-" + UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 16);
    }
}
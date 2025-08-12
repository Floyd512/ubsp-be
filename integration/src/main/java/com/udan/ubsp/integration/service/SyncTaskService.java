package com.udan.ubsp.integration.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.entity.SyncTaskVersionEntity;

public interface SyncTaskService extends IService<SyncTaskEntity> {

    /**
     * 创建任务（草稿或启用），初始化版本号为1
     */
    Long createTask(SyncTaskEntity task,
                    JsonNode envJson,
                    JsonNode sourceJson,
                    JsonNode transformsJson,
                    JsonNode sinkJson,
                    String renderedText,
                    JsonNode renderedJson,
                    String changeMemo);

    /**
     * 更新并创建新版本（version_no +1），可选择是否切换为当前版本
     */
    Integer createNewVersion(Long taskId,
                             JsonNode envJson,
                             JsonNode sourceJson,
                             JsonNode transformsJson,
                             JsonNode sinkJson,
                             String renderedText,
                             JsonNode renderedJson,
                             boolean switchToCurrent,
                             String changeMemo);

    /**
     * 切换版本为当前生效
     */
    void switchVersion(Long taskId, Integer versionNo);

    /**
     * 预渲染配置（将 env/source/sink 组装为 seatunnel 支持的 JSON/HOCON 文本）
     */
    String renderConfigText(JsonNode envJson, JsonNode sourceJson, JsonNode transformsJson, JsonNode sinkJson, String format);

    /**
     * 分页查询任务
     */
    IPage<SyncTaskEntity> pageTasks(Page<SyncTaskEntity> page, String keyword, String status, String sourceType, String sinkType);

    /**
     * 触发执行（仅落执行记录，提交引擎另行扩展）
     */
    Long triggerExecution(Long taskId, String triggerType);

    /**
     * 查询任务版本详情
     */
    SyncTaskVersionEntity getVersion(Long taskId, Integer versionNo);

    /**
     * 查询执行详情
     */
    SyncTaskExecutionEntity getExecution(Long executionId);
}
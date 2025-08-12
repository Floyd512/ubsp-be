package com.udan.ubsp.integration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
import com.udan.ubsp.common.utils.Result;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.entity.SyncTaskVersionEntity;
import com.udan.ubsp.integration.service.SyncTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 同步任务 Controller
 */
@Tag(name = "任务管理")
@RestController
@RequestMapping("/api/integration/task")
public class SyncTaskController {

    @Autowired
    private SyncTaskService taskService;

    // 预留：如需在controller内做轻量渲染/校验时使用
    // private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public record CreateTaskRequest(
            String taskCode,
            String taskName,
            String description,
            Long sourceDatasourceId,
            Long sinkDatasourceId,
            String sourceType,
            String sinkType,
            String jobMode,
            Integer parallelism,
            Integer checkpointIntervalMs,
            String scheduleMode,
            String cronExpression,
            String timezone,
            String status,
            JsonNode env,
            JsonNode source,
            JsonNode transforms,
            JsonNode sink,
            String renderedText,
            JsonNode renderedJson,
            String changeMemo
    ) {}

    @Operation(summary = "创建任务(含版本1)", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("create")
    public Result<Long> createTask(@RequestBody CreateTaskRequest req) {
        SyncTaskEntity task = new SyncTaskEntity();
        task.setTaskCode(req.taskCode());
        task.setTaskName(req.taskName());
        task.setDescription(req.description());
        task.setSourceDatasourceId(req.sourceDatasourceId());
        task.setSinkDatasourceId(req.sinkDatasourceId());
        task.setSourceType(req.sourceType());
        task.setSinkType(req.sinkType());
        task.setJobMode(req.jobMode());
        task.setParallelism(req.parallelism());
        task.setCheckpointIntervalMs(req.checkpointIntervalMs());
        task.setScheduleMode(req.scheduleMode());
        task.setCronExpression(req.cronExpression());
        task.setTimezone(req.timezone());
        task.setStatus(req.status());
        Long id = taskService.createTask(task, req.env(), req.source(), req.transforms(), req.sink(), req.renderedText(), req.renderedJson(), req.changeMemo());
        return Result.ok(id);
    }

    public record UpdateVersionRequest(
            Long taskId,
            JsonNode env,
            JsonNode source,
            JsonNode transforms,
            JsonNode sink,
            String renderedText,
            JsonNode renderedJson,
            Boolean switchToCurrent,
            String changeMemo
    ) {}

    @Operation(summary = "创建新版本", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("version/create")
    public Result<Integer> createVersion(@RequestBody UpdateVersionRequest req) {
        Integer versionNo = taskService.createNewVersion(
                req.taskId(), req.env(), req.source(), req.transforms(), req.sink(), req.renderedText(), req.renderedJson(), Boolean.TRUE.equals(req.switchToCurrent()), req.changeMemo());
        return Result.ok(versionNo);
    }

    @Operation(summary = "切换当前版本", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("version/switch")
    public Result<Void> switchVersion(@RequestParam Long taskId, @RequestParam Integer versionNo) {
        taskService.switchVersion(taskId, versionNo);
        return Result.ok(null);
    }

    public record RenderRequest(JsonNode env, JsonNode source, JsonNode transforms, JsonNode sink, String format) {}

    @Operation(summary = "预渲染配置为文本", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("render")
    public Result<String> render(@RequestBody RenderRequest req) {
        String text = taskService.renderConfigText(req.env(), req.source(), req.transforms(), req.sink(), req.format());
        return Result.ok(text);
    }

    @Operation(summary = "分页查询任务列表", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("page")
    public Result<IPage<SyncTaskEntity>> page(@RequestParam long current,
                                              @RequestParam long size,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) String status,
                                              @RequestParam(required = false) String sourceType,
                                              @RequestParam(required = false) String sinkType) {
        IPage<SyncTaskEntity> page = taskService.pageTasks(new Page<>(current, size), keyword, status, sourceType, sinkType);
        return Result.ok(page);
    }

    @Operation(summary = "触发执行", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("trigger")
    public Result<Long> trigger(@RequestParam Long taskId, @RequestParam(defaultValue = "MANUAL") String triggerType) {
        Long execId = taskService.triggerExecution(taskId, triggerType);
        return Result.ok(execId);
    }

    @Operation(summary = "查看版本详情", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("version/detail")
    public Result<SyncTaskVersionEntity> versionDetail(@RequestParam Long taskId, @RequestParam Integer versionNo) {
        return Result.ok(taskService.getVersion(taskId, versionNo));
    }

    @Operation(summary = "查看执行详情", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("execution/detail")
    public Result<SyncTaskExecutionEntity> executionDetail(@RequestParam Long executionId) {
        return Result.ok(taskService.getExecution(executionId));
    }
}
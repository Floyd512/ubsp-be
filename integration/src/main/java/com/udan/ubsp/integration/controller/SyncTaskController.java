package com.udan.ubsp.integration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.udan.ubsp.common.utils.Result;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.exception.UBSPException;
import com.udan.ubsp.integration.dto.CreateTaskDTO;
import com.udan.ubsp.integration.dto.RenderConfigDTO;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.dto.UpdateVersionDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.entity.SyncTaskExecutionEntity;
import com.udan.ubsp.integration.entity.SyncTaskVersionEntity;
import com.udan.ubsp.integration.service.SyncTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 同步任务 Controller
 */
@Tag(name = "任务管理")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/integration/task")
public class SyncTaskController {

    private final SyncTaskService taskService;

    @Operation(summary = "创建任务(含版本1)")
    @PostMapping("create")
    public Result<Long> createTask(@RequestBody @Valid CreateTaskDTO dto) {
        Long id = taskService.createTask(dto);
        return Result.ok(id);
    }

    @Operation(summary = "创建新版本")
    @PostMapping("version/create")
    public Result<Integer> createVersion(@RequestBody @Valid UpdateVersionDTO dto) {
        Integer versionNo = taskService.createNewVersion(dto);
        return Result.ok(versionNo);
    }

    @Operation(summary = "切换当前版本")
    @PostMapping("version/switch")
    public Result<Void> switchVersion(@RequestParam Long taskId, @RequestParam Integer versionNo) {
        taskService.switchVersion(taskId, versionNo);
        return Result.ok();
    }

    @Operation(summary = "预渲染配置为文本")
    @PostMapping("render")
    public Result<String> render(@RequestBody @Valid RenderConfigDTO dto) {
        String text = taskService.renderConfigText(
                dto.getEnv(),
                dto.getSource(),
                dto.getTransforms(),
                dto.getSink(),
                dto.getFormat()
        );
        return Result.ok(text);
    }

    @Operation(summary = "分页查询任务列表")
    @GetMapping("page")
    public Result<IPage<SyncTaskEntity>> page(@RequestParam long current,
                                              @RequestParam long size,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) String status,
                                              @RequestParam(required = false) String sourceType,
                                              @RequestParam(required = false) String sinkType) {
        IPage<SyncTaskEntity> page = taskService.pageTasks(new Page<>(current, size), keyword, status, sourceType,
                sinkType);
        return Result.ok(page);
    }

    @Operation(summary = "触发执行")
    @PostMapping("trigger")
    public Result<Long> trigger(@RequestParam Long taskId, @RequestParam(defaultValue = "MANUAL") String triggerType) {
        Long execId = taskService.triggerExecution(taskId, triggerType);
        return Result.ok(execId);
    }

    @Operation(summary = "查看版本详情")
    @GetMapping("version/detail")
    public Result<SyncTaskVersionEntity> versionDetail(@RequestParam Long taskId, @RequestParam Integer versionNo) {
        return Result.ok(taskService.getVersion(taskId, versionNo));
    }

    @Operation(summary = "查看执行详情")
    @GetMapping("execution/detail")
    public Result<SyncTaskExecutionEntity> executionDetail(@RequestParam Long executionId) {
        return Result.ok(taskService.getExecution(executionId));
    }

    @Operation(summary = "保存或更新任务")
    @PostMapping("saveOrUpdate")
    public Result<Long> saveOrUpdateTask(@RequestBody @Valid SaveOrUpdateTaskDTO dto) {
        Long taskId = taskService.saveOrUpdateTask(dto);
        return Result.ok(taskId);
    }

    @Operation(summary = "获取任务详情")
    @GetMapping("detail/{id}")
    public Result<SyncTaskEntity> getTaskDetail(@PathVariable Long id) {
        SyncTaskEntity task = taskService.getById(id);
        if (task == null) {
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);
        }
        return Result.ok(task);
    }
}
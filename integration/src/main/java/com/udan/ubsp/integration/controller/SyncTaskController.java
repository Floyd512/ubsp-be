package com.udan.ubsp.integration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.udan.ubsp.common.utils.Result;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.exception.UBSPException;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
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

    @Operation(summary = "保存或更新任务")
    @PostMapping("saveOrUpdate")
    public Result<Long> saveOrUpdateTask(@RequestBody @Valid SaveOrUpdateTaskDTO dto) {
        Long taskId = taskService.saveOrUpdateTask(dto);
        return Result.ok(taskId);
    }

    @Operation(summary = "分页查询任务列表")
    @GetMapping("page")
    public Result<IPage<SyncTaskEntity>> page(@RequestParam long current,
            @RequestParam long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) String sinkType) {
        IPage<SyncTaskEntity> page = taskService.pageTasks(new Page<>(current, size), keyword, status, sourceType,
                sinkType);
        return Result.ok(page);
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

    @Operation(summary = "删除任务")
    @DeleteMapping("delete/{id}")
    public Result<Void> deleteTask(@PathVariable Long id) {
        taskService.removeById(id);
        return Result.ok();
    }

}
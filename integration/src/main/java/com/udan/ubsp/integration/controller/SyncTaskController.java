package com.udan.ubsp.integration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.udan.ubsp.common.utils.Result;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.exception.UBSPException;
import com.udan.ubsp.integration.dto.ExecuteTaskDTO;
import com.udan.ubsp.integration.dto.SaveOrUpdateTaskDTO;
import com.udan.ubsp.integration.entity.SyncTaskEntity;
import com.udan.ubsp.integration.service.SeaTunnelApiService;
import com.udan.ubsp.integration.service.SyncTaskService;
import com.udan.ubsp.integration.vo.TaskExecutionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import com.udan.ubsp.common.interceptor.AuthenticationInterceptor;
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
    private final SeaTunnelApiService seaTunnelApiService;

    @Operation(summary = "保存或更新任务")
    @PostMapping("saveOrUpdate")
    public Result<Long> saveOrUpdateTask(@RequestBody @Valid SaveOrUpdateTaskDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(AuthenticationInterceptor.USER_ID_ATTR);
        // 将 userId 放入 DTO 供服务层使用（需要在 DTO 中新增两个字段或改服务层直接使用 userId）
        Long taskId = taskService.saveOrUpdateTask(dto, userId);
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

    @Operation(summary = "执行任务")
    @PostMapping("execute")
    public Result<TaskExecutionVO> executeTask(@RequestBody @Valid ExecuteTaskDTO dto) {
        TaskExecutionVO result = taskService.executeTask(dto.getTaskId());
        return Result.ok(result);
    }

    @Operation(summary = "获取SeaTunnel作业信息")
    @GetMapping("job-info/{jobId}")
    public Result<Object> getJobInfo(@PathVariable String jobId) {
        Object jobInfo = seaTunnelApiService.getJobInfo(jobId);
        return Result.ok(jobInfo);
    }

}
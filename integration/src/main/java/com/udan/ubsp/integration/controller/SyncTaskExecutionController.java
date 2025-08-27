package com.udan.ubsp.integration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.udan.ubsp.common.utils.Result;
import com.udan.ubsp.integration.service.SyncTaskExecutionFileService;
import com.udan.ubsp.integration.service.SyncTaskExecutionService;
import com.udan.ubsp.integration.vo.FileSummaryVO;
import com.udan.ubsp.integration.vo.TaskExecutionDetailVO;
import com.udan.ubsp.integration.dto.InitExecutionDTO;
import com.udan.ubsp.integration.dto.FinalizeExecutionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "任务执行记录")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/integration/task-exec")
public class SyncTaskExecutionController {

	private final SyncTaskExecutionService executionService;
	private final SyncTaskExecutionFileService executionFileService;

	@Operation(summary = "根据任务ID分页查询执行历史记录")
	@GetMapping("task/{taskId}/history")
	public Result<IPage<TaskExecutionDetailVO>> getHistoryByTaskId(
			@PathVariable Long taskId,
			@RequestParam(defaultValue = "1") Long current,
			@RequestParam(defaultValue = "10") Long size) {
		IPage<TaskExecutionDetailVO> history = executionService.getExecutionHistoryByTaskId(taskId, current, size);
		return Result.ok(history);
	}

	@Operation(summary = "初始化执行记录")
	@PostMapping("init")
	public Result<Long> init(@RequestBody @jakarta.validation.Valid InitExecutionDTO dto) {
		Long id = executionService.initExecution(dto);
		return Result.ok(id);
	}

	@Operation(summary = "完成执行记录并回填统计")
	@PostMapping("finalize")
	public Result<Void> finalizeExec(@RequestBody @jakarta.validation.Valid FinalizeExecutionDTO dto) {
		executionService.finalizeExecution(dto);
		return Result.ok();
	}

	@Operation(summary = "按作业ID扫描并入库文件明细与数量")
	@PostMapping("job/{jobId}/files/sync")
	public Result<FileSummaryVO> syncFiles(@PathVariable String jobId) {
		FileSummaryVO summary = executionFileService.syncFilesByJobId(jobId);
		return Result.ok(summary);
	}
}
package com.udan.ubsp.integration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskExecutionDetailVO {

	// 执行记录信息
	@Schema(description = "任务ID")
	private Long taskId;

	@Schema(description = "SeaTunnel作业ID")
	private String seaTunnelJobId;

	@Schema(description = "执行状态")
	private String status;

	@Schema(description = "开始时间")
	private LocalDateTime startTime;

	@Schema(description = "结束时间")
	private LocalDateTime endTime;

	@Schema(description = "持续时长(秒)")
	private Long duration;

	@Schema(description = "处理记录数")
	private Long recordCount;

	@Schema(description = "错误信息")
	private String errorMessage;

	// 文件信息列表
	@Schema(description = "生成的文件列表")
	private List<ExecutionFileVO> files;

	@Data
	public static class ExecutionFileVO {
		@Schema(description = "文件名称")
		private String fileName;

		@Schema(description = "文件大小(字节)")
		private Long fileSize;

		@Schema(description = "创建时间")
		private LocalDateTime createTime;
	}
}

package com.udan.ubsp.integration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FinalizeExecutionDTO {

	@NotNull
	@Schema(description = "SeaTunnel作业ID")
	private String seaTunnelJobId;

	@Schema(description = "最终状态(PENDING/RUNNING/SUCCESS/FAILED/KILLED/TIMEOUT)")
	private String status;

	@Schema(description = "开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	@Schema(description = "结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;

	@Schema(description = "执行时长（秒）")
	private Long duration;

	@Schema(description = "处理记录数")
	private Long recordCount;

	@Schema(description = "错误信息")
	private String errorMessage;
}



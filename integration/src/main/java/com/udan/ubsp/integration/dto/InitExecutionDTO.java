package com.udan.ubsp.integration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InitExecutionDTO {

	@NotNull
	@Schema(description = "任务ID")
	private Long taskId;

	@NotNull
	@Schema(description = "SeaTunnel作业ID")
	private String jobId;
}



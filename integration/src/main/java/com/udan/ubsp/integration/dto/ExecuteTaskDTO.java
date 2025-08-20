package com.udan.ubsp.integration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Description 执行任务请求DTO
 * @Author TOM FORD
 */
@Data
@Schema(description = "执行任务请求DTO")
public class ExecuteTaskDTO {

    @NotNull(message = "任务ID不能为空")
    @Schema(description = "任务ID", example = "1")
    private Long taskId;
}

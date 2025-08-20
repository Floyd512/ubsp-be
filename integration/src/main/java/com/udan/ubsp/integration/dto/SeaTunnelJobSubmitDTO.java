package com.udan.ubsp.integration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

/**
 * @Description SeaTunnel任务提交DTO
 * @Author TOM FORD
 */
@Data
@Schema(description = "SeaTunnel任务提交DTO")
public class SeaTunnelJobSubmitDTO {

    @Schema(description = "作业ID")
    private String jobId;

    @NotBlank(message = "作业名称不能为空")
    @Schema(description = "作业名称")
    private String jobName;

    @Schema(description = "是否从保存点启动")
    private Boolean isStartWithSavePoint;

    @Schema(description = "格式，固定为JSON", example = "JSON")
    private String format;

    @Schema(description = "SeaTunnel配置JSON")
    private Map<String, Object> config;
}

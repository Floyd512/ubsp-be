package com.udan.ubsp.integration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description 任务执行结果VO
 * @Author TOM FORD
 */
@Data
@Schema(description = "任务执行结果VO")
public class TaskExecutionVO {

    @Schema(description = "SeaTunnel作业ID", example = "733584788375666689")
    private String jobId;

    @Schema(description = "作业名称", example = "测试ElasticSearch导出至本地")
    private String jobName;
}

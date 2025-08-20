package com.udan.ubsp.integration.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description 保存或更新同步任务DTO
 * @Author TOM FORD
 */
@Data
@Schema(description = "保存或更新同步任务DTO")
public class SaveOrUpdateTaskDTO {

    @Schema(description = "任务ID（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "任务名称", example = "ES到本地文件-报警历史导出")
    private String taskName;

    @Schema(description = "任务描述")
    private String description;

    @Schema(description = "源数据源ID")
    private Long sourceDatasourceId;

    @Schema(description = "目标数据源ID")
    private Long sinkDatasourceId;

    @Schema(description = "源端类型", example = "elasticsearch")
    private String sourceType;

    @Schema(description = "目标端类型", example = "LocalFile")
    private String sinkType;

    @Schema(description = "并行度", example = "4")
    private Integer parallelism;

    @Schema(description = "检查点间隔(毫秒)", example = "300000")
    private Integer checkpointIntervalMs;

    @Schema(description = "状态", example = "0-未启用，1-启用")
    private Integer status;

    @Schema(description = "环境配置JSON")
    private JsonNode envJson;

    @Schema(description = "源端配置JSON")
    private JsonNode sourceJson;

    @Schema(description = "转换配置JSON")
    private JsonNode transformJson;

    @Schema(description = "目标端配置JSON")
    private JsonNode sinkJson;
}
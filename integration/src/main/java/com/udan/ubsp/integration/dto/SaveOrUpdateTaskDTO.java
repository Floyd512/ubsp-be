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

    @Schema(description = "任务编码", example = "8f3e2a6b1c9d")
    private String taskCode;

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

    @Schema(description = "作业模式", example = "BATCH")
    private String jobMode;

    @Schema(description = "并行度", example = "4")
    private Integer parallelism;

    @Schema(description = "检查点间隔(毫秒)", example = "300000")
    private Integer checkpointIntervalMs;

    @Schema(description = "调度模式", example = "MANUAL/CRON")
    private String scheduleMode;

    @Schema(description = "CRON表达式")
    private String cronExpression;

    @Schema(description = "时区", example = "Asia/Shanghai")
    private String timezone;

    @Schema(description = "状态", example = "ACTIVE/DISABLED/DRAFT/ARCHIVED")
    private String status;

    @Schema(description = "标签(JSON字符串)", example = "[\"prod\",\"deptA\"]")
    private String tags;

    @Schema(description = "环境配置JSON")
    private JsonNode env;

    @Schema(description = "源端配置JSON")
    private JsonNode source;

    @Schema(description = "转换配置JSON")
    private JsonNode transforms;

    @Schema(description = "目标端配置JSON")
    private JsonNode sink;

    @Schema(description = "渲染后的配置文本")
    private String renderedText;

    @Schema(description = "渲染后的配置JSON")
    private JsonNode renderedJson;

    @Schema(description = "是否切换为当前版本", example = "true")
    private Boolean switchToCurrent;

    @Schema(description = "变更备注")
    private String changeMemo;
}
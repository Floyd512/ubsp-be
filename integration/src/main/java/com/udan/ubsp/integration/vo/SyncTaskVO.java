package com.udan.ubsp.integration.vo;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 同步任务视图对象
 * @Author TOM FORD
 */
@Data
@Schema(description = "同步任务视图对象")
public class SyncTaskVO {

    @Schema(description = "任务ID", example = "1")
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

    @Schema(description = "当前生效版本号", example = "3")
    private Integer currentVersionNo;

    @Schema(description = "标签(JSON字符串)", example = "[\"prod\",\"deptA\"]")
    private String tags;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "版本详情")
    private VersionDetail version;

    @Data
    @Schema(description = "版本详情")
    public static class VersionDetail {
        @Schema(description = "版本号")
        private Integer versionNo;

        @Schema(description = "环境配置JSON")
        private JsonNode envJson;

        @Schema(description = "源端配置JSON")
        private JsonNode sourceJson;

        @Schema(description = "转换配置JSON")
        private JsonNode transformsJson;

        @Schema(description = "目标端配置JSON")
        private JsonNode sinkJson;

        @Schema(description = "渲染后的配置文本")
        private String renderedText;

        @Schema(description = "渲染后的配置JSON")
        private JsonNode renderedJson;

        @Schema(description = "变更备注")
        private String changeMemo;

        @Schema(description = "创建时间")
        private LocalDateTime createTime;
    }
}
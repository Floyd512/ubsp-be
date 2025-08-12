package com.udan.ubsp.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.udan.ubsp.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Description 同步任务主表：任务元数据与关键冗余字段（ubsp_sync_task）
 * @Author TOM FORD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ubsp_di_sync_task")
@Schema(description = "同步任务实体")
public class SyncTaskEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("task_code")
    @Schema(description = "任务编码", example = "8f3e2a6b1c9d")
    private String taskCode;

    @TableField("task_name")
    @Schema(description = "任务名称", example = "ES到本地文件-报警历史导出")
    private String taskName;

    @TableField("description")
    @Schema(description = "任务描述")
    private String description;

    @TableField("source_datasource_id")
    @Schema(description = "源数据源ID")
    private Long sourceDatasourceId;

    @TableField("sink_datasource_id")
    @Schema(description = "目标数据源ID")
    private Long sinkDatasourceId;

    @TableField("source_type")
    @Schema(description = "源端类型", example = "elasticsearch")
    private String sourceType;

    @TableField("sink_type")
    @Schema(description = "目标端类型", example = "LocalFile")
    private String sinkType;

    @TableField("job_mode")
    @Schema(description = "作业模式", example = "BATCH")
    private String jobMode;

    @TableField("parallelism")
    @Schema(description = "并行度", example = "4")
    private Integer parallelism;

    @TableField("checkpoint_interval_ms")
    @Schema(description = "检查点间隔(毫秒)", example = "300000")
    private Integer checkpointIntervalMs;

    @TableField("schedule_mode")
    @Schema(description = "调度模式", example = "MANUAL/CRON")
    private String scheduleMode;

    @TableField("cron_expression")
    @Schema(description = "CRON表达式")
    private String cronExpression;

    @TableField("timezone")
    @Schema(description = "时区", example = "Asia/Shanghai")
    private String timezone;

    @TableField("status")
    @Schema(description = "状态", example = "ACTIVE/DISABLED/DRAFT/ARCHIVED")
    private String status;

    @TableField("current_version_no")
    @Schema(description = "当前生效版本号", example = "3")
    private Integer currentVersionNo;

    @TableField("tags")
    @Schema(description = "标签(JSON字符串)", example = "[\"prod\",\"deptA\"]")
    private String tags;
}
package com.udan.ubsp.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.udan.ubsp.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Description ubsp_sync_task 实体类
 * @Author TOM FORD
 * @Date 2025-08-05 17:22:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ubsp_sync_task")
@Schema(description = "同步数据源实体")
public class SyncTaskEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("task_name")
    @Schema(description = "任务名称", example = "ElasticSearch 导数")
    private String taskName;

    @TableField("task_code")
    @Schema(description = "任务编码", example = "UUID 12位")
    private String taskCode;

    @TableField("source_datasource_id")
    @Schema(description = "源数据源ID", example = "1")
    private Long sourceDatasourceId;

    @TableField("target_datasource_id")
    @Schema(description = "目标数据源ID", example = "1")
    private Long targetDatasourceId;

    @TableField("sync_type")
    @Schema(description = "同步类型(1:全量同步,2:增量同步)", example = "1, 2")
    private Integer syncType;

    @TableField("sync_mode")
    @Schema(description = "同步模式(1:手动执行,2:定时调度)", example = "1, 2")
    private Integer syncMode;

    @TableField("cron_expression")
    @Schema(description = "Cron表达式(定时调度时使用)", example = "0 0 2 * * ?")
    private String cronExpression;

    @TableField("source_config")
    @Schema(description = "源端配置(JSON格式:表名、字段、条件等)", example = "{\"table\":\"users\",\"fields\":[\"id\",\"name\"]}")
    private String sourceConfig;

    @TableField("target_config")
    @Schema(description = "目标端配置(JSON格式:表名、字段映射等)", example = "{\"table\":\"dw_users\",\"fieldMap\":{\"id\":\"user_id\"}}")
    private String targetConfig;

    @TableField("task_config")
    @Schema(description = "同步配置(JSON格式:批次大小、并发度等)", example = "{\"batchSize\":1000,\"concurrency\":4}")
    private String taskConfig;

    @TableField("status")
    @Schema(description = "状态(0:禁用,1:启用)", example = "1")
    private Integer status;

    @TableField("created_by")
    @Schema(description = "创建人ID", example = "1001")
    private Long createdBy;

    @TableField("updated_by")
    @Schema(description = "更新人ID", example = "1002")
    private Long updatedBy;
}
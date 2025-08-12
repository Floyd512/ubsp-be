package com.udan.ubsp.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import com.udan.ubsp.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * @Description 同步任务执行记录（ubsp_sync_task_execution）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ubsp_di_sync_task_execution")
@Schema(description = "同步任务执行记录实体")
public class SyncTaskExecutionEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("task_id")
    @Schema(description = "任务ID")
    private Long taskId;

    @TableField("version_no")
    @Schema(description = "执行所用版本号")
    private Integer versionNo;

    @TableField("trigger_type")
    @Schema(description = "触发类型", example = "MANUAL/CRON/RETRY/RECOVERY")
    private String triggerType;

    @TableField("status")
    @Schema(description = "状态", example = "PENDING/RUNNING/SUCCESS/FAILED/KILLED/TIMEOUT")
    private String status;

    @TableField("engine_job_id")
    @Schema(description = "引擎返回的任务ID")
    private String engineJobId;

    @TableField("cluster_endpoint")
    @Schema(description = "提交的集群/API地址")
    private String clusterEndpoint;

    @TableField("start_time")
    @Schema(description = "开始时间")
    private Date startTime;

    @TableField("end_time")
    @Schema(description = "结束时间")
    private Date endTime;

    @TableField("duration_ms")
    @Schema(description = "持续时长(毫秒)")
    private Long durationMs;

    @TableField("log_path")
    @Schema(description = "日志路径/对象存储Key")
    private String logPath;

    @TableField("error_message")
    @Schema(description = "错误信息")
    private String errorMessage;

    @TableField(value = "metrics_json")
    @Schema(description = "指标JSON")
    private JsonNode metricsJson;
}



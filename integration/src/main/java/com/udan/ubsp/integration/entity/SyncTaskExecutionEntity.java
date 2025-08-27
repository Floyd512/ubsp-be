package com.udan.ubsp.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.udan.ubsp.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @Description 同步任务执行记录表实体（ubsp_di_sync_task_execution）
 * @Author TOM FORD
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

    @TableField("sea_tunnel_job_id")
    @Schema(description = "SeaTunnel任务ID")
    private String seaTunnelJobId;

	@TableField("status")
	@Schema(description = "执行状态", example = "PENDING/RUNNING/SUCCESS/FAILED/KILLED/TIMEOUT")
	private String status;

	@TableField("start_time")
	@Schema(description = "开始时间")
	private LocalDateTime startTime;

	@TableField("end_time")
	@Schema(description = "结束时间")
	private LocalDateTime endTime;

	@TableField("duration")
	@Schema(description = "持续时长(秒)")
	private Long duration;

	@TableField("record_count")
	@Schema(description = "处理记录数")
	private Long recordCount;

	@TableField("error_message")
	@Schema(description = "错误信息")
	private String errorMessage;
}



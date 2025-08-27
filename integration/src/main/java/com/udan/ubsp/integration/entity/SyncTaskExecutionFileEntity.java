package com.udan.ubsp.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.udan.ubsp.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Description 同步任务执行文件明细表实体（ubsp_di_sync_task_execution_file）
 * @Author TOM FORD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ubsp_di_sync_task_execution_file")
@Schema(description = "同步任务执行文件明细实体")
public class SyncTaskExecutionFileEntity extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableField("sea_tunnel_job_id")
	@Schema(description = "SeaTunnel任务ID")
	private Long seaTunnelJobId;

	@TableField("file_name")
	@Schema(description = "文件名称")
	private String fileName;

	@TableField("file_size")
	@Schema(description = "文件大小(字节)")
	private Long fileSize;
}
package com.udan.ubsp.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
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

    @TableField("source_type")
    @Schema(description = "源端类型", example = "elasticsearch")
    private String sourceType;

    @TableField("sink_type")
    @Schema(description = "目标端类型", example = "LocalFile")
    private String sinkType;

    @TableField("parallelism")
    @Schema(description = "并行度", example = "4")
    private Integer parallelism;

    @TableField("checkpoint_interval_ms")
    @Schema(description = "检查点间隔(毫秒)", example = "300000")
    private Integer checkpointIntervalMs;

    @TableField("status")
    @Schema(description = "状态", example = "0-未启用，1-启用")
    private Integer status;

    @TableField("create_user_id")
    @Schema(description = "创建人ID")
    private Long createUserId;

    @TableField("update_user_id")
    @Schema(description = "更新人ID")
    private Long updateUserId;

    @TableField("env_json")
    @Schema(description = "环境配置JSON")
    private JsonNode envJson;

    @TableField("source_json")
    @Schema(description = "源端配置JSON")
    private JsonNode sourceJson;

    @TableField("transform_json")
    @Schema(description = "转换配置JSON")
    private JsonNode transformJson;

    @TableField("sink_json")
    @Schema(description = "目标端配置JSON")
    private JsonNode sinkJson;

    @TableField(exist = false)
    @Schema(description = "创建人姓名")
    private String createUserName;

    @TableField(exist = false)
    @Schema(description = "更新人姓名")
    private String updateUserName;
}
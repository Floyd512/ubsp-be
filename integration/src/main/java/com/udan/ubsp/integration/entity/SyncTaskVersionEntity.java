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
 * @Description 同步任务版本表（ubsp_sync_task_version）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ubsp_di_sync_task_version")
@Schema(description = "同步任务版本实体")
public class SyncTaskVersionEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("task_id")
    @Schema(description = "任务ID")
    private Long taskId;

    @TableField("version_no")
    @Schema(description = "版本号")
    private Integer versionNo;

    @TableField("sea_tunnel_version")
    @Schema(description = "SeaTunnel 版本", example = "2.3.11")
    private String seaTunnelVersion;

    @TableField("config_format")
    @Schema(description = "配置格式", example = "JSON/HOCON")
    private String configFormat;

    @TableField(value = "env_json")
    @Schema(description = "Env JSON")
    private JsonNode envJson;

    @TableField(value = "source_json")
    @Schema(description = "Source JSON")
    private JsonNode sourceJson;

    @TableField(value = "transforms_json")
    @Schema(description = "Transforms JSON，可为空")
    private JsonNode transformsJson;

    @TableField(value = "sink_json")
    @Schema(description = "Sink JSON")
    private JsonNode sinkJson;

    @TableField("rendered_config_text")
    @Schema(description = "渲染后的配置文本(JSON或HOCON)")
    private String renderedConfigText;

    @TableField(value = "rendered_config_json")
    @Schema(description = "渲染后的配置JSON，可选")
    private JsonNode renderedConfigJson;

    @TableField("change_memo")
    @Schema(description = "变更备注")
    private String changeMemo;

    @TableField("config_hash")
    @Schema(description = "配置哈希(md5)")
    private String configHash;
}



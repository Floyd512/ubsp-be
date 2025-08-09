package com.udan.bdsp.integration.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import com.udan.bdsp.common.entity.BaseEntity;
import com.udan.bdsp.common.enums.BaseStatusEnum;
import com.udan.bdsp.integration.enums.NodeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Description 数据源配置模板表 实体类
 * @Author TOM FORD
 * @Date 2025-08-07 14:26:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ubsp_di_config_template")
@Schema(description = "数据源类型实体")
public class ConfigTemplateEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("template_name")
    @Schema(description = "模板名称", example = "MySQL-Source")
    private String templateName;

    @TableField("node_type")
    @Schema(description = "节点类型(1:source,2:sink)", example = "1")
    private NodeTypeEnum nodeType;

    @TableField("type_code")
    @Schema(description = "数据源类型编码", example = "101")
    private Integer typeCode;

    @TableField("field_definitions")
    @Schema(description = "字段定义(前端表单配置)")
    private JsonNode fieldDefinitions;

    @TableField("config_template")
    @Schema(description = "配置模板(后端SeaTunnel配置)")
    private JsonNode configTemplate;

    @TableField("version")
    @Schema(description = "模板版本", example = "1.0")
    private String version;

    @TableField("status")
    @Schema(description = "状态(0:禁用,1:启用)", example = "1")
    private BaseStatusEnum status;
}
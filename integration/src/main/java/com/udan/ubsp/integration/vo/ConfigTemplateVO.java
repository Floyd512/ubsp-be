package com.udan.ubsp.integration.vo;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 配置模板VO
 * @Author TOM FORD
 * @Date 2025-08-07
 */
@Data
@NoArgsConstructor
@Schema(description = "配置模板信息")
public class ConfigTemplateVO {

    @Schema(description = "模板ID", example = "1")
    private Long id;

    @Schema(description = "模板名称", example = "MySQL-Source")
    private String templateName;

    @Schema(description = "节点类型", example = "1")
    private Integer nodeType;

    @Schema(description = "节点类型名称", example = "source")
    private String nodeTypeName;

    @Schema(description = "数据源类型编码", example = "101")
    private Integer typeCode;

    @Schema(description = "字段定义(前端表单配置)")
    private JsonNode fieldDefinitions;

    @Schema(description = "配置模板(后端SeaTunnel配置)")
    private JsonNode configTemplate;

    @Schema(description = "模板版本", example = "1.0")
    private String version;

    @Schema(description = "状态", example = "1")
    private Integer status;
}
package com.udan.bdsp.integration.vo;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 数据源类型VO
 * @Author TOM FORD
 * @Date 2025-07-22 14:11:48
 */
@Data
@NoArgsConstructor
@Schema(description = "数据源类型信息")
public class SyncDataSourceTypeVO {

    @Schema(description = "数据源类型编码", example = "101")
    private Integer code;

    @Schema(description = "数据源类型名称", example = "MySQL")
    private String name;

    @Schema(description = "数据源分类编码", example = "1")
    private Integer categoryCode;

    @Schema(description = "数据源分类名称", example = "关系型数据库")
    private String categoryName;

    @Schema(description = "默认端口", example = "3306")
    private Integer defaultPort;

    @Schema(description = "是否需要认证", example = "true")
    private Integer requiresAuth;

    @Schema(description = "是否需要数据库名", example = "true")
    private Integer requiresDatabase;

    @Schema(description = "配置模板(JSON格式)")
    private JsonNode configTemplate;

    @Schema(description = "字段定义(JSON格式)")
    private JsonNode fieldDefinitions;

    @Schema(description = "状态", example = "1")
    private Integer status;
} 
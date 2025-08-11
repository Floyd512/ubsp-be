package com.udan.ubsp.integration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 数据源类型及支持的节点类型VO
 * @Author TOM FORD
 * @Date 2025-08-07
 */
@Data
@NoArgsConstructor
@Schema(description = "数据源类型及支持的节点类型信息")
public class SourceTypeWithNodeVO {

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

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "支持的节点类型列表", example = "[1, 2]")
    private List<Integer> supportedNodeTypes;
}
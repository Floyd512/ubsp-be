package com.udan.bdsp.integration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 数据源支持的节点类型VO
 * @Author TOM FORD
 * @Date 2025-08-07
 */
@Data
@NoArgsConstructor
@Schema(description = "数据源支持的节点类型信息")
public class SupportedNodeTypesVO {

    @Schema(description = "数据源类型编码", example = "101")
    private Integer typeCode;

    @Schema(description = "支持的节点类型列表", example = "[1, 2]")
    private List<Integer> supportedNodeTypes;
}
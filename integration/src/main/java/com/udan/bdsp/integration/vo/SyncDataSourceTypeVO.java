package com.udan.bdsp.integration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 数据源类型VO
 * @Author TOM FORD
 * @Date 2025-07-22 14:11:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "数据源类型信息")
public class SyncDataSourceTypeVO {

    @Schema(description = "数据源类型编码")
    private Integer code;

    @Schema(description = "数据源类型名称")
    private String name;

    @Schema(description = "默认端口")
    private Integer defaultPort;
} 
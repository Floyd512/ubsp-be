package com.udan.bdsp.integration.vo;

import com.udan.bdsp.integration.enums.DataSourceCategoryEnum;
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

    @Schema(description = "数据源类型编码")
    private Integer code;

    @Schema(description = "数据源类型名称")
    private String name;

    @Schema(description = "默认端口")
    private Integer defaultPort;

    @Schema(description = "数据源分类")
    private Integer categoryCode;

    @Schema(description = "数据源分类名称")
    private String categoryName;

    public SyncDataSourceTypeVO(Integer code, String name, Integer defaultPort, DataSourceCategoryEnum categoryEnum) {
        this.code = code;
        this.name = name;
        this.defaultPort = defaultPort;
        this.categoryCode = categoryEnum.getCode();
        this.categoryName = categoryEnum.getName();
    }
} 
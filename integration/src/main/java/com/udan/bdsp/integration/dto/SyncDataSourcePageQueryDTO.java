package com.udan.bdsp.integration.dto;

import com.udan.bdsp.common.enums.BaseStatusEnum;
import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author TOM FORD
 * @Date 2025-07-21 18:25:06
 */
@Data
@Schema(description = "数据源分页查询DTO")
public class SyncDataSourcePageQueryDTO {

    @Schema(description = "数据源名称")
    private String dataSourceName;

    @Schema(description = "数据源类型", example = "1")
    private DataSourceTypeEnum dataSourceType;

    @Schema(description = "状态", example = "1", allowableValues = {"0", "1"})
    private BaseStatusEnum status;
}
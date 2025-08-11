package com.udan.ubsp.integration.dto;

import com.udan.ubsp.common.enums.BaseStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author TOM FORD
 * @Date 2025-07-23 14:28:10
 */
@Data
@Schema(description = "更改数据源状态DTO")
public class UpdateDataSourceStatusDTO {

    @Schema(description = "数据源ID")
    private Long id;

    @Schema(description = "数据源状态")
    private BaseStatusEnum status;
}

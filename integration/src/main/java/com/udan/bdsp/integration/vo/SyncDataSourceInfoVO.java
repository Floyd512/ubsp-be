package com.udan.bdsp.integration.vo;

import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Description
 * @Author TOM FORD
 * @Date 2025-07-21 19:56:25
 */
public class SyncDataSourceInfoVO extends SyncDataSourceEntity {

    @Schema(description = "创建人姓名")
    private String createdByName;

    @Schema(description = "更新人姓名")
    private String updatedByName;
}

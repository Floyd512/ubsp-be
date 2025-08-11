package com.udan.ubsp.integration.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.udan.ubsp.integration.entity.SyncDataSourceEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 数据源列表展示VO
 * @Author TOM FORD
 * @Date 2025-07-21 19:56:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SyncDataSourceInfoVO extends SyncDataSourceEntity {

    @Schema(description = "创建人姓名")
    @TableField(value = "created_by_name")
    private String createdByName;

    @Schema(description = "更新人姓名")
    @TableField(value = "updated_by_name")
    private String updatedByName;
}

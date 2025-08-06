package com.udan.bdsp.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.udan.bdsp.common.entity.BaseEntity;
import com.udan.bdsp.common.enums.BaseStatusEnum;
import com.udan.bdsp.integration.enums.DataSourceCategoryEnum;
import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @Description 数据源类型配置表
 * @Author TOM FORD
 * @Date 2025-08-06 11:04:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ubsp_sync_data_source_type")
@Schema(description = "数据源类型实体")
public class SyncDataSourceTypeEntity extends BaseEntity {

    @TableField("type_code")
    @Schema(description = "数据源类型编码", example = "101")
    private DataSourceTypeEnum typeCode;

    @TableField("category_code")
    @Schema(description = "数据源分类编码", example = "RELATIONAL_DB")
    private DataSourceCategoryEnum categoryCode;

    @TableField("default_port")
    @Schema(description = "默认端口", example = "3306")
    private Integer defaultPort;

    @TableField("requires_auth")
    @Schema(description = "是否需要认证(0:不需要,1:需要)", example = "1")
    private Integer requiresAuth;

    @TableField("requires_database")
    @Schema(description = "是否需要数据库名", example = "1")
    private Integer requiresDatabase;

    @TableField("config_template")
    @Schema(description = "配置模板(JSON格式)")
    private JsonNode configTemplate;

    @TableField("field_definitions")
    @Schema(description = "字段定义(JSON格式)")
    private JsonNode fieldDefinitions;

    @TableField("status")
    @Schema(description = "状态", example = "1")
    private BaseStatusEnum status;
}
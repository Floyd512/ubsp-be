package com.udan.ubsp.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.udan.ubsp.common.entity.BaseEntity;
import com.udan.ubsp.common.enums.BaseStatusEnum;
import com.udan.ubsp.integration.enums.DataSourceCategoryEnum;
import com.udan.ubsp.integration.enums.DataSourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;


/**
 * @Description 数据源类型配置表
 * @Author TOM FORD
 * @Date 2025-08-06 11:04:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ubsp_di_data_source_type")
@Schema(description = "数据源类型实体")
public class DataSourceTypeEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("type_code")
    @Schema(description = "数据源类型编码", example = "101")
    private DataSourceTypeEnum typeCode;

    @TableField("category_code")
    @Schema(description = "数据源分类编码", example = "数据源分类编码(1:关系型数据库,2:NoSQL,3:消息队列,4:搜索引擎,5:文件存储)")
    private DataSourceCategoryEnum categoryCode;

    @TableField("description")
    @Schema(description = "描述", example = "MySQL数据库、PostgreSQL数据库")
    private String description;

    @TableField("default_port")
    @Schema(description = "默认端口", example = "3306")
    private Integer defaultPort;

    @TableField("requires_auth")
    @Schema(description = "是否需要认证(0:不需要,1:需要)", example = "1")
    private Integer requiresAuth;

    @TableField("requires_database")
    @Schema(description = "是否需要数据库名(0:不需要,1:需要)", example = "1")
    private Integer requiresDatabase;

    @TableField("status")
    @Schema(description = "状态", example = "1")
    private BaseStatusEnum status;
}
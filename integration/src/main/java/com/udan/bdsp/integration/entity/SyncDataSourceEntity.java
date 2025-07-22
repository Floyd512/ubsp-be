package com.udan.bdsp.integration.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.udan.bdsp.common.entity.BaseEntity;
import com.udan.bdsp.common.enums.BaseStatusEnum;
import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Description 系统用户实体类
 * @Author TOM FORD
 * @Date 2025-07-16 11:35:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bdsp_sync_datasource")
@Schema(description = "同步数据源实体")
public class SyncDataSourceEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("datasource_name")
    @Schema(description = "数据源名称", example = "生产MySQL")
    private String datasourceName;

    @TableField("datasource_type")
    @Schema(description = "数据源类型", example = "1", allowableValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9"})
    private DataSourceTypeEnum datasourceType;

    @TableField("host")
    @Schema(description = "主机地址", example = "192.168.1.10")
    private String host;

    @TableField("port")
    @Schema(description = "端口号", example = "3306")
    private Integer port;

    @TableField("username")
    @Schema(description = "用户名", example = "root")
    private String username;

    @TableField(value = "password" , select = false)
    @Schema(description = "密码(A加密)", example = "e10adc3949ba59abbe56e057f20f883")
    private String password;

    @TableField("connection_params")
    @Schema(description = "连接参数（JSON格式）", example = "")
    private String connectionParams;

    @TableField("description")
    @Schema(description = "描述", example = "小旦在线线上可读库")
    private String description;

    @TableField("status")
    @Schema(description = "状态", example = "1", allowableValues = {"0", "1"})
    private BaseStatusEnum status;

    @TableField("created_id")
    @Schema(description = "创建人ID", example = "001")
    private Long createdId;

    @TableField("updated_id")
    @Schema(description = "更新人ID", example = "001")
    private Long updatedId;
}
package com.udan.bdsp.integration.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.udan.bdsp.common.entity.BaseEntity;
import com.udan.bdsp.common.enums.BaseStatusEnum;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author TOM FORD
 * @Date 2025-07-23 20:55:18
 */
@Data
@Schema(description = "创建或更新数据源DTO")
public class SaveOrUpdateDataSourceDTO {

    @Schema(description = "数据源ID", example = "1")
    private Long id;

    @Schema(description = "数据源名称", example = "生产MySQL")
    private String dataSourceName;

    @Schema(description = "数据源类型", example = "1")
    private DataSourceTypeEnum dataSourceType;

    @Schema(description = "主机地址", example = "192.168.1.10")
    private String host;

    @Schema(description = "端口号", example = "3306")
    private Integer port;

    @Schema(description = "用户名", example = "root")
    private String username;

    @Schema(description = "密码", example = "123456")
    private String password;

    @Schema(description = "连接参数（JSON格式）", example = "")
    private String connectionParams;

    @Schema(description = "描述", example = "小旦在线线上可读库")
    private String description;
}
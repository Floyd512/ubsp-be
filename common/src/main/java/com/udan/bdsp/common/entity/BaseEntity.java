package com.udan.bdsp.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description entity 父类
 * @Author TOM FORD
 * @Date 2025-07-03 20:37:14
 */
@Data
public class BaseEntity implements Serializable {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
//    @JsonIgnore
    private Date createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
//    @JsonIgnore
    private Date updateTime;

    @Schema(description = "逻辑删除")
    @TableLogic //逻辑删除功能
    @TableField("is_deleted")
    @JsonIgnore
    private Byte isDeleted;
}
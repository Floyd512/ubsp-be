package com.udan.bdsp.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.udan.bdsp.common.entity.BaseEntity;
import com.udan.bdsp.common.enums.BaseStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @Description 系统用户实体类
 * @Author TOM FORD
 * @Date 2025-07-03 20:16:38
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@Schema(description = "用户实体")
public class SystemUserEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableField("username")
    @Schema(description = "用户名", example = "admin")
    private String username;

    @TableField(value = "password", select = false)
    @Schema(description = "密码(加密)", example = "$2a$10$...")
    private String password;

    @TableField("real_name")
    @Schema(description = "真实姓名", example = "TOM FORD")
    private String realName;

    @TableField("email")
    @Schema(description = "邮箱", example = "admin@company.com")
    private String email;

    @TableField("phone")
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @TableField("avatar")
    @Schema(description = "头像URL", example = "/avatar/admin.jpg")
    private String avatar;

    @TableField("account_status")
    @Schema(description = "状态", example = "1", allowableValues = {"0", "1"})
    private BaseStatusEnum accountStatus;

    @TableField("last_login_time")
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;


    @TableField("last_login_ip")
    @Schema(description = "最后登录IP")
    private String lastLoginIp;

    @TableField("remark")
    @Schema(description = "备注")
    private String remark;
}
package com.udan.bdsp.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.udan.bdsp.common.entity.BaseEntity;
import com.udan.bdsp.system.enums.UserStatus;
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
@Schema(description = "用户信息")
public class SystemUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("username")
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 密码(加密)
     */
    @TableField("password")
    @Schema(description = "密码(加密)", example = "$2a$10$...")
    private String password;

    /**
     * 邮箱
     */
    @TableField("email")
    @Schema(description = "邮箱", example = "admin@company.com")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    /**
     * 头像URL
     */
    @TableField("avatar")
    @Schema(description = "头像URL", example = "/avatar/admin.jpg")
    private String avatar;

    /**
     * 状态(0:禁用,1:启用)
     */
    @TableField("account_status")
    @Schema(description = "状态", example = "1", allowableValues = {"0", "1"})
    private UserStatus accountStatus;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;
}
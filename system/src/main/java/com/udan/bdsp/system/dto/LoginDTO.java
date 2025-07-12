package com.udan.bdsp.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description 接收请求登录数据(DTO)
 * @Author TOM FORD
 * @Date 2025-07-12 13:39:11
 */
@Data
@Schema(description = "登录")
public class LoginDTO {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "验证码key")
    private String captchaKey;

    @Schema(description = "验证码code")
    private String captchaCode;
}
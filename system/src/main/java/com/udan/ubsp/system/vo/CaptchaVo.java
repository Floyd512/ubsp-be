package com.udan.ubsp.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description 图形验证码VO
 * @Author TOM FORD
 * @Date 2025-07-12 13:36:00
 */
@Data
@Schema(description = "图像验证码")
@AllArgsConstructor
public class CaptchaVo {

    @Schema(description = "验证码图片信息")
    private String image;

    @Schema(description = "验证码key")
    private String key;
}
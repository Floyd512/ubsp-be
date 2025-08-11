package com.udan.ubsp.common.enums;

import lombok.Getter;

/**
 * @Description 统一返回结果状态信息枚举
 * @Author TOM FORD
 * @Date 2025-07-12 10:58:01
 */
@Getter
public enum ResultCodeEnum {

    // 通用
    COMMON_SUCCESS(200, "成功"),
    COMMON_FAIL(201, "失败"),
    COMMON_PARAM_ERROR(202, "参数不正确"),
    COMMON_SERVICE_ERROR(203, "服务异常"),
    ILLEGAL_REQUEST(204, "非法请求"),
    REPEAT_SUBMIT(205, "重复提交"),

    // 管理后台
    ADMIN_ACCOUNT_EXIST(301, "账号已存在"),
    ADMIN_CAPTCHA_CODE_ERROR(302, "验证码错误"),
    ADMIN_CAPTCHA_CODE_EXPIRED(303, "验证码已过期"),
    ADMIN_CAPTCHA_CODE_NOT_FOUND(304, "未输入验证码"),
    ADMIN_LOGIN_AUTH(305, "未登陆"),
    ADMIN_ACCOUNT_NOT_EXIST_ERROR(306, "账号不存在"),
    ADMIN_ACCOUNT_ERROR(307, "用户名或密码错误"),
    ADMIN_ACCOUNT_DISABLED_ERROR(308, "该用户已被禁用"),
    ADMIN_ACCESS_FORBIDDEN(309, "无访问权限"),
    ADMIN_ACCOUNT_DISABLED(310, "该用户已被禁用"),


    // 数据集成
    INTEGRATION_UNSUPPORTED_DATA_TYPE(411, "不支持的数据类型"),
    INTEGRATION_CONFIG_TEMPLATE_NOT_FOUND(412, "未找到对应的配置模板"),


    // Token
    TOKEN_EXPIRED(601, "token过期"),
    TOKEN_INVALID(602, "token非法");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
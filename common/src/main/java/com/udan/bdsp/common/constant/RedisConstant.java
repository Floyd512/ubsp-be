package com.udan.bdsp.common.constant;

/**
 * @Description Redis常量类
 * @Author TOM FORD
 * @Date 2025-07-12 13:54:29
 */
public class RedisConstant {

    // 防止实例化
    private RedisConstant() {}

    public static final String SYSTEM_LOGIN_PREFIX = "system:login:";
    public static final Integer SYSTEM_LOGIN_CAPTCHA_TTL_SEC = 60;
}
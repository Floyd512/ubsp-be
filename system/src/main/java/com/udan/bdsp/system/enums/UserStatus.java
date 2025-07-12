package com.udan.bdsp.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;

/**
 * @Description 状态枚举
 * @Author TOM FORD
 * @Date 2025-07-03 20:31:21
 */
public enum UserStatus implements BaseEnum {

    ENABLE(1, "正常"),

    DISABLE(0, "禁用");


    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    UserStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
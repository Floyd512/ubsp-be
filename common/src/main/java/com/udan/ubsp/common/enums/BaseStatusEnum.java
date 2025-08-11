package com.udan.ubsp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Description 状态枚举
 * @Author TOM FORD
 * @Date 2025-07-03 20:31:21
 */
public enum BaseStatusEnum implements BaseEnum {

    ENABLE(1, "正常"),

    DISABLE(0, "禁用");


    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    BaseStatusEnum(Integer code, String name) {
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
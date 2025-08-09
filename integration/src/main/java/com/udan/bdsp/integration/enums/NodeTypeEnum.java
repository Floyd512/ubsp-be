package com.udan.bdsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;

import java.util.Arrays;

/**
 * @Description 节点类型枚举
 * @Author TOM FORD
 * @Date 2025-08-07
 */
public enum NodeTypeEnum implements BaseEnum {

    SOURCE(1, "source", "数据源"),
    SINK(2, "sink", "数据目标");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    private final String description;

    NodeTypeEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * 根据代码获取枚举
     */
    public static NodeTypeEnum getByCode(Integer code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据名称获取枚举
     */
    public static NodeTypeEnum getByName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 检查节点类型是否存在
     */
    public static boolean isValidType(Integer code) {
        return getByCode(code) != null;
    }
}
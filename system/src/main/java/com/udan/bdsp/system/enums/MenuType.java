package com.udan.bdsp.system.enums;

import com.udan.bdsp.common.enums.BaseEnum;

/**
 * @Description 菜单类型枚举
 * @Author TOM FORD
 * @Date 2025-01-21 10:00:00
 */
public enum MenuType implements BaseEnum {

    DIRECTORY(1, "目录"),

    MENU(2, "菜单"),

    BUTTON(3, "按钮");

    private final Integer code;

    private final String name;

    MenuType(Integer code, String name) {
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
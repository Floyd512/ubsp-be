package com.udan.bdsp.system.enums;

import com.udan.bdsp.common.enums.BaseEnum;

/**
 * @Description 数据权限类型枚举
 * @Author TOM FORD
 * @Date 2025-01-21 10:00:00
 */
public enum PermissionType implements BaseEnum {

    SELF_ONLY(1, "仅自己"),

    DEPT_ONLY(2, "本部门"),

    ALL_DATA(3, "全部");

    private final Integer code;

    private final String name;

    PermissionType(Integer code, String name) {
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
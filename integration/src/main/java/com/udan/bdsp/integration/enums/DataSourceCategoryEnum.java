package com.udan.bdsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;

/**
 * @Description 数据源分类枚举
 * @Author TOM FORD
 * @Date 2025-07-23 11:00:55
 */
public enum DataSourceCategoryEnum implements BaseEnum {

    RELATIONAL_DB(1, "关系型数据库"),
    NOSQL(2, "NoSQL"),
    MQ(3, "消息队列"),
    FILE_STORAGE(4, "文件存储");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    DataSourceCategoryEnum(Integer code, String name) {
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

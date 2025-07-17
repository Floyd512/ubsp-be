package com.udan.bdsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;

/**
 * 同步类型枚举
 * 
 * @Description 数据同步类型，支持全量同步和增量同步
 * @Author BDSP Team
 * @Date 2025-01-16
 */
public enum SyncTypeEnum implements BaseEnum {

    FULL(1, "全量同步"),
    INCREMENTAL(2, "增量同步");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    SyncTypeEnum(Integer code, String name) {
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

    /**
     * 根据编码获取枚举
     * 
     * @param code 编码
     * @return 同步类型枚举
     */
    public static SyncTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (SyncTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     * 
     * @param name 名称
     * @return 同步类型枚举
     */
    public static SyncTypeEnum getByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (SyncTypeEnum type : values()) {
            if (type.getName().equalsIgnoreCase(name.trim())) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否为全量同步
     * 
     * @return true-全量同步，false-非全量同步
     */
    public boolean isFull() {
        return this == FULL;
    }

    /**
     * 是否为增量同步
     * 
     * @return true-增量同步，false-非增量同步
     */
    public boolean isIncremental() {
        return this == INCREMENTAL;
    }

    /**
     * 获取同步策略描述
     * 
     * @return 同步策略描述
     */
    public String getDescription() {
        switch (this) {
            case FULL:
                return "完整复制源数据，适用于数据量较小或首次同步";
            case INCREMENTAL:
                return "仅同步变更数据，适用于大数据量的定期同步";
            default:
                return "";
        }
    }
}
package com.udan.bdsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;

/**
 * 同步模式枚举
 * 
 * @Description 数据同步执行模式，支持手动执行和定时调度
 * @Author BDSP Team
 * @Date 2025-01-16
 */
public enum SyncModeEnum implements BaseEnum {

    MANUAL(1, "手动执行"),
    SCHEDULED(2, "定时调度");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    SyncModeEnum(Integer code, String name) {
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
     * @return 同步模式枚举
     */
    public static SyncModeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (SyncModeEnum mode : values()) {
            if (mode.getCode().equals(code)) {
                return mode;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     * 
     * @param name 名称
     * @return 同步模式枚举
     */
    public static SyncModeEnum getByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (SyncModeEnum mode : values()) {
            if (mode.getName().equalsIgnoreCase(name.trim())) {
                return mode;
            }
        }
        return null;
    }

    /**
     * 是否为手动执行
     * 
     * @return true-手动执行，false-非手动执行
     */
    public boolean isManual() {
        return this == MANUAL;
    }

    /**
     * 是否为定时调度
     * 
     * @return true-定时调度，false-非定时调度
     */
    public boolean isScheduled() {
        return this == SCHEDULED;
    }

    /**
     * 是否需要Cron表达式
     * 
     * @return true-需要Cron表达式，false-不需要
     */
    public boolean requiresCronExpression() {
        return this == SCHEDULED;
    }

    /**
     * 获取执行模式描述
     * 
     * @return 执行模式描述
     */
    public String getDescription() {
        switch (this) {
            case MANUAL:
                return "用户手动触发执行，适用于临时性或测试性同步";
            case SCHEDULED:
                return "系统定时自动执行，适用于周期性数据同步";
            default:
                return "";
        }
    }
}
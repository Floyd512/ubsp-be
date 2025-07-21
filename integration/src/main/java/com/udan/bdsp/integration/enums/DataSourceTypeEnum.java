package com.udan.bdsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;

/**
 * @Description 数据源类型枚举，支持数据库、中间件和文件存储，可用于Source和Sink
 * @Author BDSP Team
 * @Date 2025-07-16 11:21:09
 */
public enum DataSourceTypeEnum implements BaseEnum {

    // 关系型数据库
    MYSQL(1, "MySQL"),
    POSTGRESQL(2, "PostgreSQL"),

    // NoSQL数据库
    REDIS(3, "Redis"),
    ELASTICSEARCH(4, "ElasticSearch"),

    // 消息队列
    KAFKA(5, "Kafka"),

    // 文件存储
    LOCAL_FILE(6, "本地文件"),
    OSS_FILE(7, "阿里云OSS"),
    HDFS_FILE(8, "HDFS"),
    MINIO_FILE(9, "MinIO");

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    DataSourceTypeEnum(Integer code, String name) {
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
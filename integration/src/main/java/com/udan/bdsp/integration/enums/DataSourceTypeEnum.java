package com.udan.bdsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;
import lombok.Getter;

/**
 * @Description 数据源类型枚举，支持数据库、中间件和文件存储，可用于Source和Sink
 * @Author BDSP Team
 * @Date 2025-07-16 11:21:09
 */
public enum DataSourceTypeEnum implements BaseEnum {

    // 关系型数据库
    MYSQL(1, "MySQL", "MySQL关系型数据库", 3306),
    POSTGRESQL(2, "PostgreSQL", "PostgreSQL关系型数据库", 5432),

    // NoSQL数据库
    REDIS(3, "Redis", "Redis内存数据库", 6379),
    ELASTICSEARCH(4, "ElasticSearch", "ElasticSearch搜索引擎", 9200),

    // 消息队列
    KAFKA(5, "Kafka", "Apache Kafka消息队列", 9092),

    // 文件存储
    LOCAL_FILE(6, "本地文件", "本地文件系统存储", null),
    HDFS_FILE(7, "HDFS", "Hadoop分布式文件系统", 8020),
    OSS_FILE(8, "阿里云OSS", "阿里云对象存储服务", null),
    MINIO_FILE(9, "MinIO", "MinIO对象存储", 9000);

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    @Getter
    private final String description;

    @Getter
    private final Integer defaultPort;

    DataSourceTypeEnum(Integer code, String name, String description, Integer defaultPort) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.defaultPort = defaultPort;
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
package com.udan.bdsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.bdsp.common.enums.BaseEnum;
import lombok.Getter;

/**
 * @Description 数据源类型枚举，支持数据库、中间件和文件存储，可用于Source和Sink
 * @Author TOM FORD
 * @Date 2025-07-21 11:21:09
 */
public enum DataSourceTypeEnum implements BaseEnum {

    // 关系型数据库
    MYSQL(1, "MySQL", 3306, DataSourceCategoryEnum.RELATIONAL_DB),
    POSTGRESQL(2, "PostgreSQL", 5432, DataSourceCategoryEnum.RELATIONAL_DB),

    // NoSQL数据库
    REDIS(3, "Redis", 6379, DataSourceCategoryEnum.NOSQL),
    ELASTICSEARCH(4, "ElasticSearch", 9200, DataSourceCategoryEnum.NOSQL),

    // 消息队列
    KAFKA(5, "Kafka", 9092, DataSourceCategoryEnum.MQ),

    // 文件存储
    LOCAL_FILE(6, "本地文件", null, DataSourceCategoryEnum.FILE_STORAGE),
    HDFS_FILE(7, "HDFS", 8020, DataSourceCategoryEnum.FILE_STORAGE),
    OSS_FILE(8, "阿里云OSS", null, DataSourceCategoryEnum.FILE_STORAGE),
    MINIO_FILE(9, "MinIO", 9000, DataSourceCategoryEnum.FILE_STORAGE);

    @EnumValue
    @JsonValue
    private final Integer code;

    private final String name;

    @Getter
    private final Integer defaultPort;

    @Getter
    private final DataSourceCategoryEnum category;

    DataSourceTypeEnum(Integer code, String name, Integer defaultPort, DataSourceCategoryEnum category) {
        this.code = code;
        this.name = name;
        this.defaultPort = defaultPort;
        this.category = category;
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
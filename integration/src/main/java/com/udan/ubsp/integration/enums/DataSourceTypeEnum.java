package com.udan.ubsp.integration.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.udan.ubsp.common.enums.BaseEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Description 数据源类型枚举，支持数据库、中间件和文件存储，可用于Source和Sink
 * @Author TOM FORD
 * @Date 2025-07-21 11:21:09
 */
public enum DataSourceTypeEnum implements BaseEnum {

    // 关系型数据库
    MYSQL(101, "MySQL", 3306, DataSourceCategoryEnum.RELATIONAL_DB),
    POSTGRESQL(102, "PostgreSQL", 5432, DataSourceCategoryEnum.RELATIONAL_DB),
    DORIS(103, "Doris", 9030, DataSourceCategoryEnum.RELATIONAL_DB),
    CLICKHOUSE(104, "ClickHouse", 8123, DataSourceCategoryEnum.RELATIONAL_DB),

    // NoSQL数据库
    REDIS(201, "Redis", 6379, DataSourceCategoryEnum.NOSQL),

    // 搜索引擎
    ELASTICSEARCH(301, "ElasticSearch", 9200, DataSourceCategoryEnum.SEARCH_ENGINE),

    // 消息队列
    KAFKA(401, "Kafka", 9092, DataSourceCategoryEnum.MQ),

    // 文件存储
    LOCAL_FILE(501, "本地文件", null, DataSourceCategoryEnum.FILE_STORAGE),
    HDFS_FILE(502, "HDFS", 8020, DataSourceCategoryEnum.FILE_STORAGE),
    OSS_FILE(503, "阿里云OSS", null, DataSourceCategoryEnum.FILE_STORAGE),
    MINIO_FILE(504, "MinIO", 9000, DataSourceCategoryEnum.FILE_STORAGE);


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

    /**
     * 判断是否需要认证
     */
    public Integer requiresAuth() {
        int flag = 0;
        if (this == MYSQL || this == POSTGRESQL || this == CLICKHOUSE || this == DORIS || this == OSS_FILE || this == MINIO_FILE) {
            flag = 1;
        }
        return flag;
    }

    /**
     * 判断是否需要数据库名
     */
    public Integer requiresDatabase() {
        int flag = 0;
        if (this == MYSQL || this == POSTGRESQL || this == CLICKHOUSE || this == DORIS) {
            flag = 1;
        }
        return flag;
    }

    /**
     * 根据代码获取枚举
     */
    public static DataSourceTypeEnum getByCode(Integer code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    /**
     * 检查数据源类型是否存在
     */
    public static boolean isValidType(Integer code) {
        return getByCode(code) != null;
    }
}
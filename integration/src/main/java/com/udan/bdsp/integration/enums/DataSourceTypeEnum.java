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

    /**
     * 根据编码获取枚举
     */
    public static DataSourceTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (DataSourceTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     */
    public static DataSourceTypeEnum getByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (DataSourceTypeEnum type : values()) {
            if (type.getName().equalsIgnoreCase(name.trim())) {
                return type;
            }
        }
        return null;
    }

    // ==================== 数据源分类方法 ====================

    /**
     * 检查是否为关系型数据库
     */
    public boolean isRelationalDatabase() {
        return this == MYSQL || this == POSTGRESQL;
    }

    /**
     * 检查是否为NoSQL数据库
     */
    public boolean isNoSqlDatabase() {
        return this == REDIS || this == ELASTICSEARCH;
    }

    /**
     * 检查是否为消息队列
     */
    public boolean isMessageQueue() {
        return this == KAFKA;
    }

    /**
     * 检查是否为文件存储
     */
    public boolean isFileStorage() {
        return this == LOCAL_FILE || this == OSS_FILE || this == HDFS_FILE || this == MINIO_FILE;
    }

    /**
     * 检查是否为云存储
     */
    public boolean isCloudStorage() {
        return this == OSS_FILE;
    }

    /**
     * 检查是否为对象存储
     */
    public boolean isObjectStorage() {
        return this == OSS_FILE || this == MINIO_FILE;
    }

    // ==================== Source/Sink 支持检查 ====================

    /**
     * 检查是否可以作为数据源(Source)
     */
    public boolean canBeSource() {
        // 所有类型都可以作为数据源
        return true;
    }

    /**
     * 检查是否可以作为数据目标(Sink)
     */
    public boolean canBeSink() {
        // 所有类型都可以作为数据目标
        return true;
    }

    /**
     * 检查是否只能作为Sink（通常是文件存储）
     */
    public boolean isSinkOnly() {
        // 目前所有类型都支持双向，但可以根据业务需要调整
        return false;
    }

    // ==================== 连接信息方法 ====================

    /**
     * 获取默认端口
     */
    public Integer getDefaultPort() {
        return switch (this) {
            case MYSQL -> 3306;
            case POSTGRESQL -> 5432;
            case REDIS -> 6379;
            case KAFKA -> 9092;
            case ELASTICSEARCH -> 9200;
            default -> null; // 文件存储类型不需要端口
        };
    }

    /**
     * 获取JDBC驱动类名
     */
    public String getDriverClassName() {
        return switch (this) {
            case MYSQL -> "com.mysql.cj.jdbc.Driver";
            case POSTGRESQL -> "org.postgresql.Driver";
            default -> null; // 非JDBC数据源
        };
    }

    /**
     * 获取连接URL模板
     */
    public String getUrlTemplate() {
        return switch (this) {
            case MYSQL ->
                    "jdbc:mysql://{host}:{port}/{database}?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
            case POSTGRESQL -> "jdbc:postgresql://{host}:{port}/{database}";
            case REDIS -> "redis://{host}:{port}/{database}";
            case ELASTICSEARCH -> "https://{host}:{port}";
            case KAFKA -> "{host}:{port}";
            default -> null; // 文件存储类型使用路径而非URL
        };
    }

    // ==================== 文件存储相关方法 ====================

    /**
     * 获取文件路径模板
     */
    public String getFilePathTemplate() {
        return switch (this) {
            case LOCAL_FILE -> "/data/bdsp/files/{taskCode}/{date}/{filename}";
            case OSS_FILE -> "bdsp-bucket/data/{taskCode}/{date}/{filename}";
            case HDFS_FILE -> "/bdsp/data/{taskCode}/{date}/{filename}";
            case MINIO_FILE -> "bdsp/data/{taskCode}/{date}/{filename}";
            default -> null;
        };
    }

    /**
     * 获取支持的文件格式
     */
    public String[] getSupportedFileFormats() {
        if (isFileStorage()) {
            return new String[] { "csv", "json", "parquet", "txt", "xlsx" };
        }
        return new String[0];
    }

    // ==================== 业务描述方法 ====================

    /**
     * 获取数据源类型描述
     */
    public String getDescription() {
        return switch (this) {
            case MYSQL -> "MySQL关系型数据库，支持ACID事务";
            case POSTGRESQL -> "PostgreSQL开源关系型数据库";
            case REDIS -> "Redis内存数据库，支持多种数据结构";
            case ELASTICSEARCH -> "ElasticSearch分布式搜索引擎";
            case KAFKA -> "Apache Kafka分布式消息队列";
            case LOCAL_FILE -> "本地文件系统存储";
            case OSS_FILE -> "阿里云对象存储服务";
            case HDFS_FILE -> "Hadoop分布式文件系统";
            case MINIO_FILE -> "MinIO开源对象存储";
        };
    }

    /**
     * 获取图标名称（用于前端显示）
     */
    public String getIconName() {
        return switch (this) {
            case MYSQL -> "mysql-icon";
            case POSTGRESQL -> "postgresql-icon";
            case REDIS -> "redis-icon";
            case ELASTICSEARCH -> "elasticsearch-icon";
            case KAFKA -> "kafka-icon";
            case LOCAL_FILE -> "folder-icon";
            case OSS_FILE -> "aliyun-icon";
            case HDFS_FILE -> "hadoop-icon";
            case MINIO_FILE -> "minio-icon";
        };
    }
}
package com.udan.bdsp.integration.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Properties;

/**
 * @Description 解析连接参数
 * @Author TOM FORD
 * @Date 2025-07-16 12:00:18
 */
public class DataSourceConfigParser {

    /**
     * 解析 MySQL 参数
     * @param connectionParams 连接参数字符串
     * @return Properties
     */
    public Properties parseMysqlConfig(String connectionParams) {
        JSONObject config = JSON.parseObject(connectionParams);
        Properties props = new Properties();

        props.setProperty("useSSL", config.getString("useSSL"));
        props.setProperty("serverTimezone", config.getString("serverTimezone"));
        props.setProperty("useUnicode", config.getString("useUnicode"));
        return props;
    }

//    public JedisPoolConfig parseRedisConfig(String connectionParams) {
//        JSONObject config = JSON.parseObject(connectionParams);
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//
//        poolConfig.setMaxTotal(config.getInteger("maxTotal"));
//        poolConfig.setMaxIdle(config.getInteger("maxIdle"));
//        poolConfig.setMinIdle(config.getInteger("minIdle"));
//        // ... 其他参数
//
//        return poolConfig;
//    }
}

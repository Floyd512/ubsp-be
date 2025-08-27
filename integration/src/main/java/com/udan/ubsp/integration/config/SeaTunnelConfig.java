package com.udan.ubsp.integration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description SeaTunnel配置类
 * @Author TOM FORD
 */
@Data
@Component
@ConfigurationProperties(prefix = "seatunnel.api")
public class SeaTunnelConfig {

    /**
     * SeaTunnel API基础URL
     */
    private String baseUrl;

    /**
     * 认证方式：none, basic, token
     */
    private String authType = "none";

    /**
     * Basic认证用户名
     */
    private String username;

    /**
     * Basic认证密码
     */
    private String password;

    /**
     * Token认证的token值
     */
    private String token;

    /**
     * 自定义认证头名称
     */
    private String authHeader = "Authorization";

    /**
     * 同步文件目标父路径
     */
    private String outputRoot;
}

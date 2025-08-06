package com.udan.bdsp.start.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description Knife4j API文档配置
 * 配置位置：start模块，统一管理所有模块的API文档
 * <p>
 * 访问地址：<a href="http://localhost:8080/doc.html">接口文档 API</a>
 * @Author TOM FORD
 * @Date 2025-07-01 20:37:10
 */

@Configuration
public class Knife4jConfiguration {

    /**
     * 全局API信息配置
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UBSP大数据服务平台API文档")
                        .description("UDAN BigData Service Platform - 一站式大数据服务平台")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("TOM FORD")
                                .email("bdsp@udantech.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components().addSecuritySchemes("Authorization",
                        new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("请输入token")
                ));
    }

    /**
     * 系统管理模块API分组
     */
    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder()
                .group("01-系统管理")
                .pathsToMatch("/api/system/**")
                .build();
    }

    /**
     * 数据集成模块API分组
     */
    @Bean
    public GroupedOpenApi dataIntegrationApi() {
        return GroupedOpenApi.builder()
                .group("02-数据集成")
                .pathsToMatch("/api/integration/**")
                .build();
    }

    /**
     * 数据开发模块API分组
     */
    @Bean
    public GroupedOpenApi dataDevApi() {
        return GroupedOpenApi.builder()
                .group("03-数据开发")
                .pathsToMatch("/api/datadev/**")
                .build();
    }

    /**
     * 数据分析模块API分组
     */
    @Bean
    public GroupedOpenApi dataAnalysisApi() {
        return GroupedOpenApi.builder()
                .group("04-数据分析")
                .pathsToMatch("/api/analysis/**")
                .build();
    }

    /**
     * 平台服务模块API分组
     */
    @Bean
    public GroupedOpenApi platformServiceApi() {
        return GroupedOpenApi.builder()
                .group("05-数据服务")
                .pathsToMatch("/api/service/**")
                .build();
    }
}

package com.udan.config;

import com.udan.bdsp.common.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 配置请求拦截
 * @Author TOM FORD
 * @Date 2025-07-14 20:04:28
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/system/**") // 拦截所有系统管理相关接口
                .excludePathPatterns(
                    "/api/system/login",
                    "/api/system/captcha"
                ); // 登录、验证码接口放行
    }
}
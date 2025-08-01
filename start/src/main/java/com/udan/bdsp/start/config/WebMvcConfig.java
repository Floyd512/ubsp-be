package com.udan.bdsp.start.config;

import com.udan.bdsp.common.interceptor.AuthenticationInterceptor;
import com.udan.bdsp.start.converter.StringToBaseEnumConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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

    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(stringToBaseEnumConverterFactory);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns(
                        "/api/system/**",
                        "/api/integration/**"
                )
                .excludePathPatterns("/api/system/login", "/api/system/captcha"); // 登录、验证码接口放行
    }
}
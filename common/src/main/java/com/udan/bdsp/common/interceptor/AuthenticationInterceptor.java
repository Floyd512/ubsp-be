package com.udan.bdsp.common.interceptor;

import com.udan.bdsp.common.exception.LeaseException;
import com.udan.bdsp.common.enums.ResultCodeEnum;
import com.udan.bdsp.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @Description 请求拦截
 * @Author TOM FORD
 * @Date 2025-07-14 20:04:28
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    public static final String USER_ID_ATTR = "CURRENT_USER_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 放行OPTIONS预检请求，解决跨域问题
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH); // 未登录
        }
        Claims claims = JwtUtil.parseToken(token);
        Long userId = claims.get("userId", Long.class);
        // 将userId存入request属性，后续controller可直接获取
        request.setAttribute(USER_ID_ATTR, userId);
        return true;
    }
} 
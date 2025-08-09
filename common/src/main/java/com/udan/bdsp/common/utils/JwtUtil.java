package com.udan.bdsp.common.utils;

import com.udan.bdsp.common.exception.LeaseException;
import com.udan.bdsp.common.enums.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @Description JWT 工具类
 * @Author TOM FORD
 * @Date 2025-07-12 15:28:08
 */
public class JwtUtil {

    // 秘钥
    private static final SecretKey secretKey = Keys.hmacShaKeyFor("CY29Eb04RPNyQPxACH2jBNWFGn0ypMhc".getBytes());

    /**
     * 创建 Token
     * @param userId 用户ID
     * @param username 用户名
     * @return Token String
     */
    public static String createToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject("LOGIN_USER")
                .setExpiration(new Date(System.currentTimeMillis() + 3600000 * 24 * 365L))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析 token
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token) {

        if (token == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH); // 无token，未登录
        }

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }
    }
}
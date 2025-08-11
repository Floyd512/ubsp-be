package com.udan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description UBSP 启动类
 * @Author TOM FORD
 * @Date 2025-06-30 23:12:57
 */
@SpringBootApplication
@EnableScheduling
public class UbspApplication {
    public static void main(String[] args) {
        SpringApplication.run(UbspApplication.class, args);
    }
}
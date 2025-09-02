package com.udan.ubsp.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description UBSP 启动类
 * @Author TOM FORD
 * @Date 2025-06-30 23:12:57
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.udan.ubsp"})
@EnableScheduling
public class UbspApplication {
    public static void main(String[] args) {
        SpringApplication.run(UbspApplication.class, args);
    }

    @Bean
    public ApplicationRunner projectInfoRunner(Environment environment) {
        return args -> {
            Logger log = LoggerFactory.getLogger(UbspApplication.class);
            String bannerText = "UBSP";
            String[] profiles = environment.getActiveProfiles();
            String activeProfiles = profiles.length > 0 ? String.join(",", profiles) : "default";
            String port = environment.getProperty("server.port", "8080");

            try {
                String ascii = com.github.lalyos.jfiglet.FigletFont.convertOneLine(bannerText);
                log.info("\n{}\nEnv: {}  Port: {}", ascii, activeProfiles, port);
            } catch (Exception e) {
                log.info("Project: {}  Env: {}  Port: {}", bannerText, activeProfiles, port);
            }
        };
    }
}
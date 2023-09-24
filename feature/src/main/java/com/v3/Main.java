package com.v3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 　@Author: Bocky
 * 　@Date: ${YEAR}-${MONTH}-${DAY}-${TIME}
 * 　@Description:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.v3.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
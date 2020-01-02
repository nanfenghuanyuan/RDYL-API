package com.zh.module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zh.module.dao")
@EnableScheduling
@ComponentScan(value = {"com.zh.module.biz", "com.zh.module.service", "com.zh.module.controller", "com.zh.module.interceptor", "com.zh.module.aliyun"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}

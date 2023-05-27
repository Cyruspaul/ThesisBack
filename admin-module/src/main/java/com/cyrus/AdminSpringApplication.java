package com.cyrus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.cyrus.mapper")
@Transactional
@EnableTransactionManagement
public class AdminSpringApplication extends SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminSpringApplication.class, args);
    }
}

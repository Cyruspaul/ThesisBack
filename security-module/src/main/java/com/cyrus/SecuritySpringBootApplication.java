package com.cyrus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan(basePackages = "com.cyrus.repository")
@EnableEurekaClient
public class SecuritySpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecuritySpringBootApplication.class, args);
    }
}

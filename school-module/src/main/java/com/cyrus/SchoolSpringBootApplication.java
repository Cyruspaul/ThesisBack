package com.cyrus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.cyrus.mapper")
public class SchoolSpringBootApplication extends SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolSpringBootApplication.class, args);
    }
}

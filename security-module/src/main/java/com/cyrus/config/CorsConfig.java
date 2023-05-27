package com.cyrus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
////        return new BCryptPasswordEncoder(11);
//    }

    @Bean
    public CorsRegistry corsConfigurer() {
        return new CorsRegistry() {
            //                registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*");

            @Override
            public CorsRegistration addMapping(String pathPattern) {
                return super.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                        .allowCredentials(true)
                        .allowedHeaders("*");

            }


        };
    }
}

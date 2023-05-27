package com.cyrus.config;

import com.cyrus.filters.AuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Duration;

/**
 * The type Gateway config.
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    /**
     * Routes route locator.
     *
     * @param builder the builder
     * @return the route locator
     */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("ossModule", r -> r.path("/api/oss/**")
                        .filters(f -> f
                                .addRequestHeader("Content-Type", "multipart/form-data")
                                .filter(filter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("/gateway/error")
                                        .addStatusCode("INTERNAL_SERVER_ERROR")
                                        .setResumeWithoutError(true)
                                )
                        )
                        .uri("lb://ossModule"))

                .route("adminModule", r -> r.path("/api/admin/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("/gateway/error")
                                        .addStatusCode("INTERNAL_SERVER_ERROR")
                                        .setResumeWithoutError(true)
                                )
                        )
                        .uri("lb://adminModule"))

                .route("userModule", r -> r.path("/api/user/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("/gateway/error")
                                        .addStatusCode("INTERNAL_SERVER_ERROR")
                                        .setResumeWithoutError(true)
                                )
                        )
                        .uri("lb://userModule"))

                .route("schoolModule", r -> r.path("/api/school/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("/gateway/error")
                                        .addStatusCode("INTERNAL_SERVER_ERROR")
                                        .setResumeWithoutError(true)
                                )
                        )
                        .uri("lb://schoolModule"))

                .route("securityModule", r -> r.path("/api/auth/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("/gateway/error")
                                        .addStatusCode("INTERNAL_SERVER_ERROR")
                                        .setResumeWithoutError(true)
                                )
                        )
                        .uri("lb://securityModule"))

                .route("eureka", r -> r.path("/eureka/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("/gateway/error")
                                        .addStatusCode("INTERNAL_SERVER_ERROR")
                                        .setResumeWithoutError(true)
                                ))
                        .uri("http://localhost:8761"))

                .route("eureka", r -> r.path("/eureka/web/**")
                        .filters(f -> f
                                .filter(filter)
                                .circuitBreaker(config -> config
                                        .setFallbackUri("/gateway/error")
                                        .addStatusCode("INTERNAL_SERVER_ERROR")
                                        .setResumeWithoutError(true)
                                ))
                        .uri("http://localhost:8761"))

                .build();
    }

    /**
     * Configure json object mapper.
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper configureJson() {
        return new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
                .build();
    }

    /**
     * Customize json jackson 2 object mapper builder customizer.
     *
     * @return the jackson 2 object mapper builder customizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJson() {
        return builder -> {

            builder.indentOutput(true);
            builder.propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        };
    }


    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .build());
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultReactiveCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
    }
//    @Bean
//    public Customizer<SpringRetryCircuitBreakerFactory> defaultCustomizer() {
//        return factory -> factory.configureDefault(id -> new SpringRetryConfigBuilder(id)
//                .retryPolicy(new TimeoutRetryPolicy()).build());
//    }

}
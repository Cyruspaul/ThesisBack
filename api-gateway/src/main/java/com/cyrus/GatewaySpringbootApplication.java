package com.cyrus;

import com.cyrus.config.APIResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * The type Gateway springboot application.
 */
@SpringBootApplication
@EnableEurekaClient
@CircuitBreaker(name = "fooClient")
@Retry(name = "retryApi")
@RestController
public class GatewaySpringbootApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewaySpringbootApplication.class, args);
    }


    /**
     * Gets rest template.
     *
     * @return the rest template
     */
    @Bean
    @LoadBalanced
    @CircuitBreaker(name = "fooClient")
    @Retry(name = "retryApi")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @RequestMapping("/gateway/error")
    @ResponseBody
    public APIResponse<?> inCaseOfFailureUseThis() {
        System.out.println("------------inCaseOfFailureUseThis----------");
        return APIResponse.warning().message("SERVICE OFFLINE").code(5000);
    }


}

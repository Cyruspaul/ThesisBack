package com.cyrus.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * The type Router validator.
 */
@Component
public class RouterValidator {

    /**
     * The constant openApiEndpoints.
     */
    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/gateway/error",
//            "/api/oss/file/send",
            "/eureka"
    );

    /**
     * Verify is the pased link  Is secured.
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}

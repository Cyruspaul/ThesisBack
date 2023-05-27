
package com.cyrus.filters;

import com.alibaba.fastjson2.JSON;
import com.cyrus.config.APIResponse;
import com.cyrus.config.RouterValidator;
import com.cyrus.utils.MyJWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * The type Authentication filter.
 */
@RefreshScope
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    @Qualifier("redis_template")
    private final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    private final MyJWTUtil myJWTUtil;
    private final RouterValidator routerValidator;//custom route validator


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (routerValidator.isSecured.test(exchange.getRequest())) {

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return out(response, "NO HEADER");
            }
            String auttheader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

            if (auttheader != null && auttheader.startsWith("Bearer")) {
                auttheader = auttheader.substring(7);
            } else {
                return out(response, "Token Invalid");
            }
            try {
                String usernameFromToken = myJWTUtil.verifyToken(auttheader);
                log.error(auttheader);
                log.error(usernameFromToken);
                if (usernameFromToken == null)
                    return out(response, "USER SESSION EXPIRED, LOGIN AGAIN !");

            } catch (Exception e) {
                System.out.println(e.getCause());
                return out(response, "TOKEN IS NOT VALID, PLEASE RELOGIN");
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> out(ServerHttpResponse response, String message) {
        byte[] bits = JSON.toJSONBytes(APIResponse.error().message(message));
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        // response.setStatusCode(HttpStatus.UNAUTHORIZED);

        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
//    /*PRIVATE*/
//    private Mono<Void> onError(ServerWebExchange exchange, String err) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        System.out.println("-----------------------------------------------------------");
//        System.out.println(exchange.getRequest().getPath());
//        System.out.println(err);
//        System.out.println("-----------------------------------------------------------");
//        response.addCookie(ResponseCookie.fromClientResponse("error_message", "Error_Auth_Header").build());
//        response.getHeaders().add("error",err);
//
//        JSONObject data = new JSONObject();
//        Map<String,String> innerdata = new HashMap<>();
//       innerdata.put("data","value");
//        data.put("data",R.ok().data(innerdata));
//
//        return  response.writeWith(Mono.just(response.bufferFactory()
//                .wrap((data +"").getBytes())));
////         response.setComplete();
//    }
//
//    private String getAuthHeader(ServerHttpRequest request) {
//        return request.getHeaders().getOrEmpty("Authorization").get(0);
//    }
//
//    private boolean isAuthMissing(ServerHttpRequest request) {
//        return !request.getHeaders().containsKey("Authorization");
//    }
//
//    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
//        exchange.getRequest().mutate().build();
//    }


}


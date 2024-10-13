package com.spring.microservice.ecommercial.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth",
            "/auth/register",
            "/auth/token",
            "/auth/validate",
            "/api/product/get/**",
            "/api/product/get/**/**",
            "/api/product/get/",
            "/api/product/get",
            "/api/product/comment",
            "/api/product/comment/**",
            "/api/order/get",
            "/api/order/get/**",
            "/api/order/get/**/**",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}

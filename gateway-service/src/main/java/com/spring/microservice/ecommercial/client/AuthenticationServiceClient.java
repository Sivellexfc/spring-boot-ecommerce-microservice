package com.spring.microservice.ecommercial.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "authentication-service",path = "/auth")
public interface AuthenticationServiceClient {
    @GetMapping("/validatee")
    String validateToken(@RequestParam("token") String token);

    @GetMapping("get-roles")
    List<String> getRoles(@RequestParam("authHeader") String authHeader);
}

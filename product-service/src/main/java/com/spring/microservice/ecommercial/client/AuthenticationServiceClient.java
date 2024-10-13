package com.spring.microservice.ecommercial.client;

import com.spring.microservice.ecommercial.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "gateway-service",configuration = FeignConfig.class)
public interface AuthenticationServiceClient {

    @GetMapping("/auth/getUserIdByAuthHeader")
    String getAccountId(@RequestParam("authHeader") String authHeader);

    @GetMapping("/auth/getUserIdByAuthHeader")
    String getAccountIdByAuthHeader(@RequestParam("token") String token);

    @GetMapping("/auth/getStoreId")
    ResponseEntity<String> getUserStoreId(@RequestParam int userId,@RequestHeader("user-agent") String userAgent);

    @PostMapping("/api/order/setAlreadyRated")
    void setAlreadyRated(@RequestParam("orderItemId") long orderItemId,
                         @RequestParam("orderId") long orderId);

}


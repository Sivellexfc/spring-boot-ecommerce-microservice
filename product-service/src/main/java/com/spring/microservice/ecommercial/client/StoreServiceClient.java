package com.spring.microservice.ecommercial.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "store-service",path = "/api/store")
public interface StoreServiceClient {

    @PostMapping("/add-product")
    ResponseEntity addProduct(@RequestParam String productId,
                              @RequestParam String storeId);

}
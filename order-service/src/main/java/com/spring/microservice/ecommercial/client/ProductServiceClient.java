package com.spring.microservice.ecommercial.client;

import com.spring.microservice.ecommercial.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gateway-service")
public interface ProductServiceClient {

    @GetMapping("/api/product/get/id/{productId}")
    ProductDto getProductById(@PathVariable("productId") long productId);

}

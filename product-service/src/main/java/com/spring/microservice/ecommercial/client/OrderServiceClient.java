package com.spring.microservice.ecommercial.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service",url = "http://localhost:8383")
public interface OrderServiceClient {

    @PostMapping("/setAlreadyRated")
    void setAlreadyRated(@RequestParam("orderItemId") long orderItemId,
                                @RequestParam("orderId") long orderId);

}

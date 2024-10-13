package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.entity.Order;
import com.spring.microservice.ecommercial.service.OrderService;
import feign.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class GetController {

    private final OrderService orderService;

    public GetController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get")
    public ResponseEntity<Order> getOrder(@RequestParam String orderNumber) {
        return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber).get());
    }

    @GetMapping("/get/all/user/{userId}")
    public ResponseEntity<List<Order>> getAllOrder(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(orderService.getAllOrdersByCustomerId(Long.valueOf(userId)));
    }
}

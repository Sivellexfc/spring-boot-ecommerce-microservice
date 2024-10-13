package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.entity.Order;
import com.spring.microservice.ecommercial.entity.OrderItem;
import com.spring.microservice.ecommercial.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class PostController {

    private final OrderService orderService;

    public PostController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order,
                                             @RequestHeader("Authorization") String header) {
        return orderService.createOrder(order,header);
    }

    @PostMapping("/setAlreadyRated")
    public void setAlreadyRated(@RequestParam("orderItemId") long orderItemId,
                                @RequestParam("orderId") long orderId) {
        orderService.updateIsAlreadyRated(orderId,orderItemId);
    }

    @PutMapping("/{orderId}/items/{orderItemId}/rate")
    public ResponseEntity<OrderItem> updateIsAlreadyRated(
            @PathVariable Long orderId,
            @PathVariable Long orderItemId) {

        OrderItem updatedOrderItem = orderService.updateIsAlreadyRated(orderId, orderItemId);

        if (updatedOrderItem != null) {
            return ResponseEntity.ok(updatedOrderItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

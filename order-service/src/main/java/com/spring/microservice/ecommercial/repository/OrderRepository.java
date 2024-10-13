package com.spring.microservice.ecommercial.repository;

import com.spring.microservice.ecommercial.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByOrderNumber(String orderNumber);
    List<Order> findByCustomerId(Long customerId);
}

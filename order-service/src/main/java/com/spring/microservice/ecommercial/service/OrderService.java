package com.spring.microservice.ecommercial.service;

import com.spring.microservice.ecommercial.client.ProductServiceClient;
import com.spring.microservice.ecommercial.dto.ProductDto;
import com.spring.microservice.ecommercial.entity.Order;
import com.spring.microservice.ecommercial.entity.OrderItem;
import com.spring.microservice.ecommercial.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final JwtService jwtService;
    private final ProductServiceClient productServiceClient;

    public OrderService(OrderRepository orderRepository, JwtService jwtService, ProductServiceClient productServiceClient) {
        this.orderRepository = orderRepository;
        this.jwtService = jwtService;
        this.productServiceClient = productServiceClient;
    }

    public ResponseEntity<Order> createOrder(Order order, String header) {
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String userId = jwtService.extractUserId(token);
            order.setCustomerId(Long.parseLong(userId));
            order.setOrderDate(LocalDateTime.now());

            double totalOrderPrice = 0;

            for (OrderItem item : order.getOrderItems()) {
                ProductDto product = productServiceClient.getProductById(Long.parseLong(item.getProductId()));
                double itemTotalPrice = product.getPrice() * item.getAmount();
                item.setPrice(itemTotalPrice);
                totalOrderPrice += itemTotalPrice;
            }
            Order savedOrder = orderRepository.save(order);

            for (OrderItem item : savedOrder.getOrderItems()) {
                item.setOrder(savedOrder);
            }
            order.setTotalPrice(totalOrderPrice);
            return ResponseEntity.ok(orderRepository.save(savedOrder));
        }
        return ResponseEntity.badRequest().body(order);
    }


    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return Optional.ofNullable(orderRepository.findByOrderNumber(orderNumber));
    }

    public List<Order> getAllOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public ResponseEntity<Order> updateOrder(Long id, Order updatedOrder) {
        Optional<Order> existingOrderOpt = orderRepository.findById(id);
        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();
 //           existingOrder.setAmount(updatedOrder.getAmount());
 //           existingOrder.setProductId(updatedOrder.getProductId());
  //          existingOrder.setProductName(updatedOrder.getProductName());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setCustomerEmail(updatedOrder.getCustomerEmail());
            existingOrder.setCustomerPhone(updatedOrder.getCustomerPhone());
            existingOrder.setCustomerAddress(updatedOrder.getCustomerAddress());
            existingOrder.setCustomerCity(updatedOrder.getCustomerCity());
            existingOrder.setCustomerCountry(updatedOrder.getCustomerCountry());
            existingOrder.setAlreadyRated(updatedOrder.isAlreadyRated());
            return ResponseEntity.ok(orderRepository.save(existingOrder));
        } else {
            throw new RuntimeException("Order not found with ID: " + id);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderItem updateIsAlreadyRated(Long orderId, Long orderItemId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            for (OrderItem item : order.getOrderItems()) {
                if (item.getId().equals(orderItemId)) {
                    item.setAlreadyRated(true);
                    orderRepository.save(order);
                    return item;
                }
            }
        }
        return null; // Order veya OrderItem bulunamazsa null döner
    }


}

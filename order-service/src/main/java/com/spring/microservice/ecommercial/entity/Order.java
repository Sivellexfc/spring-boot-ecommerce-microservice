package com.spring.microservice.ecommercial.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();


    private double totalPrice;
    private boolean isAlreadyRated;


    private String status;
    private LocalDateTime orderDate;

    private long customerId;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String customerCity;
    private String customerCountry;

    public Order() {
        this.orderNumber = generateOrderNumber();
        this.isAlreadyRated = false;
        this.totalPrice = 0;
    }

    private String generateOrderNumber() {
        int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder orderNumber = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            orderNumber.append(characters.charAt(index));
        }

        return orderNumber.toString();
    }


}

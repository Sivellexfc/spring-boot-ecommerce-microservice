package com.spring.microservice.ecommercial.dto;

public record RequestProductDto(
        String id,
        String productName,
        String productId,
        String storeId,
        int stock,
        double price,
        boolean hasDiscount,
        int discount

) {
}

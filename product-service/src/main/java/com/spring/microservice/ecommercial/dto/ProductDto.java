package com.spring.microservice.ecommercial.dto;

import com.spring.microservice.ecommercial.entity.Category;

public record ProductDto(
        String productCode,
        String sellerId,
        String productName,
        long category,
        int stock,
        double price,
        boolean hasDiscount,
        int discount
) {
}

package com.spring.microservice.ecommercial.dto;

public record RequestStoreDto(
        long ownerAccountId,
        String storeName
) {
}

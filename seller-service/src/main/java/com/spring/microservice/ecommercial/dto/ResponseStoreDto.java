package com.spring.microservice.ecommercial.dto;

import java.util.List;

public record ResponseStoreDto(
        String id,
        long ownerAccountId,
        String storeName,
        List<String> inventoryList,
        double storePoint
) {

    public ResponseStoreDto(String id,
                            long ownerAccountId,
                            String storeName,
                            List<String> inventoryList,
                            double storePoint) {
        this.id = id;
        this.ownerAccountId = ownerAccountId;
        this.storeName = storeName;
        this.inventoryList = inventoryList;
        this.storePoint = storePoint;
    }
}

package com.spring.microservice.ecommercial.dto;

import java.util.List;

public record RequestUpdateStoreDto(
        long ownerAccountId,
        String storeName,
        List<String> inventoryList,
        double storePoint
) {

    public RequestUpdateStoreDto(
            long ownerAccountId,
            String storeName,
            List<String> inventoryList,
            double storePoint) {

        this.ownerAccountId = ownerAccountId;
        this.storeName = storeName;
        this.inventoryList = inventoryList;
        this.storePoint = storePoint;
    }
}

package com.spring.microservice.ecommercial.wrapper;

import com.spring.microservice.ecommercial.dto.RequestStoreDto;
import com.spring.microservice.ecommercial.dto.RequestUpdateStoreDto;
import com.spring.microservice.ecommercial.dto.ResponseStoreDto;
import com.spring.microservice.ecommercial.model.Store;

public class ModelWrapper {

    public static ResponseStoreDto entityToResponseStoreDto(Store store){
        return new ResponseStoreDto(
                store.getId(),
                store.getOwnerAccountId(),
                store.getStoreName(),
                store.getInventoryList(),
                store.getStorePoint()
        );
    }

    public static Store requestStoreDtoToEntity(RequestStoreDto requestStoreDto){
        Store store = new Store();
        store.setOwnerAccountId(requestStoreDto.ownerAccountId());
        store.setStoreName(requestStoreDto.storeName());
        return store;
    }

    public static Store requestUpdateStoreDtoToEntity(RequestUpdateStoreDto requestUpdateStoreDto){
        Store store = new Store();
        store.setStorePoint(requestUpdateStoreDto.storePoint());
        store.setOwnerAccountId(requestUpdateStoreDto.ownerAccountId());
        store.setStoreName(requestUpdateStoreDto.storeName());
        store.setInventoryList(requestUpdateStoreDto.inventoryList());
        return store;
    }




}

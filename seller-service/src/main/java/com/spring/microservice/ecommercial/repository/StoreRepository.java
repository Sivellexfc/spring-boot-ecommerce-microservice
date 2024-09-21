package com.spring.microservice.ecommercial.repository;

import com.spring.microservice.ecommercial.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StoreRepository extends MongoRepository<Store,String> {

    Store findByStoreName(String storeName);
    List<Store> findStoreByStoreNameStartingWith(String prefixStoreName);
    List<Store> findStoreByStorePointIsBetween(double firstValue, double secondValue);
    Store findStoreByOwnerAccountId(long ownerAccountId);
}

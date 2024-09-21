package com.spring.microservice.ecommercial.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "stores")
@Getter
@Setter
public class Store {

    @Id
    private String id;
    private long ownerAccountId;
    private String storeName;
    private List<String> inventoryList = new ArrayList<>();

    private double storePoint;


}

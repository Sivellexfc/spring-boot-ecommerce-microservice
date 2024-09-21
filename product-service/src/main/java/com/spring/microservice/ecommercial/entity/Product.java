package com.spring.microservice.ecommercial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeId;

    private String productName;
    private String productDescription;

    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] imageUrl;
    private String categoryName;
    private String brand;
    private double price;

    private int stock;
    private boolean hasDiscount;
    private int discount;
    private long categoryId;


}
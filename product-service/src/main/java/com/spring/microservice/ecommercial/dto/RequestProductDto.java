package com.spring.microservice.ecommercial.dto;

import com.spring.microservice.ecommercial.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestProductDto{
    private String sellerId;
    private String productName;
    private long categoryID;
    private String categoryName;
    private String productDescription;
    private int stock;
    private String brand;
    private double price;
    private boolean hasDiscount;
    private int discount;
    private String imageUrl;
}

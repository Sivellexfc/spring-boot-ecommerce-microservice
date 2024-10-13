package com.spring.microservice.ecommercial.dto;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productName;
    private double price;
    private boolean hasDiscount;
    private int discount;

}


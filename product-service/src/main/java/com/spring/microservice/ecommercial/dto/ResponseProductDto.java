package com.spring.microservice.ecommercial.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProductDto {
        private Long id;
        private String sellerId;
        private String productName;
        private String productDescription;
        private String imageUrl;
        private String categoryName;
        private String brand;
        private double price;
        private int stock;
        private boolean hasDiscount;
        private int discount;
        private long categoryId;

}

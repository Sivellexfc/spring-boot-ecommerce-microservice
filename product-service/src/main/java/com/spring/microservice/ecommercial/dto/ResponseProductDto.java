package com.spring.microservice.ecommercial.dto;

import com.spring.microservice.ecommercial.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseProductDto {
        private Long id;
        private String sellerId;
        private String productName;
        private String productDescription;
        private String image;
        private String categoryName;
        private String brand;
        private double price;
        private int stock;
        private boolean hasDiscount;
        private int discount;
        private long categoryId;
        private List<Comment> comments;

}

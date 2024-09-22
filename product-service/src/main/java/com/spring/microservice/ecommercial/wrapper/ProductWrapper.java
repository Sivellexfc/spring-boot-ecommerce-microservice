package com.spring.microservice.ecommercial.wrapper;


import com.spring.microservice.ecommercial.dto.ResponseProductDto;
import com.spring.microservice.ecommercial.dto.RequestProductDto;
import com.spring.microservice.ecommercial.entity.Product;

import java.util.Base64;

public class ProductWrapper {


    public static Product toEntity(RequestProductDto requestProductDto) {
        Product product = new Product();

        product.setProductName(requestProductDto.getProductName());
        product.setProductDescription(requestProductDto.getProductDescription());
        product.setCategoryId(requestProductDto.getCategoryID());
        product.setDiscount(requestProductDto.getDiscount());
        product.setHasDiscount(requestProductDto.isHasDiscount());
        product.setPrice(requestProductDto.getPrice());
        product.setStoreId(requestProductDto.getSellerId());
        product.setStock(requestProductDto.getStock());
        product.setBrand(requestProductDto.getBrand());
        product.setProductDescription(requestProductDto.getProductDescription());
        product.setCategoryName(requestProductDto.getCategoryName());
        return product;
    }

    public static Product toEntity(RequestProductDto requestProductDto,long productId) {
        Product product = new Product();
        product.setId(productId);

        product.setProductName(requestProductDto.getProductName());
        product.setCategoryId(requestProductDto.getCategoryID());
        product.setDiscount(requestProductDto.getDiscount());
        product.setHasDiscount(requestProductDto.isHasDiscount());
        product.setPrice(requestProductDto.getPrice());
        product.setStoreId(requestProductDto.getSellerId());
        product.setStock(requestProductDto.getStock());
        return product;
    }

    public static ResponseProductDto toResponseProductDto(Product product) {
        ResponseProductDto responseProductDto = new ResponseProductDto();

        responseProductDto.setProductName(product.getProductName());
        responseProductDto.setProductDescription(product.getProductDescription());
        responseProductDto.setCategoryId(product.getCategoryId());
        responseProductDto.setDiscount(product.getDiscount());
        responseProductDto.setHasDiscount(product.isHasDiscount());
        responseProductDto.setPrice(product.getPrice());
        responseProductDto.setBrand(product.getBrand());

        if (product.getImageUrl() != null) {
            String base64Image = Base64.getEncoder().encodeToString(product.getImageUrl());
            responseProductDto.setImageUrl("data:image/jpeg;base64," + base64Image);
        }
        responseProductDto.setStock(product.getStock());
        return responseProductDto;
    }

}

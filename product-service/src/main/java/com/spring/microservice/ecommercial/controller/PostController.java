package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.client.AuthenticationServiceClient;
import com.spring.microservice.ecommercial.dto.RequestProductDto;
import com.spring.microservice.ecommercial.entity.Product;
import com.spring.microservice.ecommercial.service.JwtService;
import com.spring.microservice.ecommercial.service.ProductService;
import com.spring.microservice.ecommercial.service.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/product")
@RestController
@CrossOrigin
public class PostController {

    private final ProductService productService;
    private final RequestService requestService;
    private final JwtService jwtService;
    private final AuthenticationServiceClient authenticationServiceClient;

    public PostController(ProductService productService, RequestService requestService, JwtService jwtService, AuthenticationServiceClient authenticationServiceClient) {
        this.productService = productService;
        this.requestService = requestService;
        this.jwtService = jwtService;
        this.authenticationServiceClient = authenticationServiceClient;
    }

    @PostMapping("/new")
    public ResponseEntity<Product> newProduct(@RequestParam("file") MultipartFile productImage,
                                              @RequestParam("productName") String productName,
                                              @RequestParam("productDescription") String productDescription,
                                              @RequestParam("categoryName") String categoryName,
                                              @RequestParam("brand") String brand,
                                              @RequestParam("price") double price,
                                              @RequestParam("stock") int stock,
                                              HttpServletRequest request) {
        System.out.println("new product controller");
        try {
            RequestProductDto requestProductDto = new RequestProductDto();
            requestProductDto.setProductName(productName);
            requestProductDto.setProductDescription(productDescription);
            requestProductDto.setCategoryName(categoryName);
            requestProductDto.setBrand(brand);
            requestProductDto.setPrice(price);
            requestProductDto.setStock(stock);
            return productService.createProduct(requestProductDto, request, productImage.getBytes());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}

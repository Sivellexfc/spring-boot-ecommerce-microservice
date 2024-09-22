package com.spring.microservice.ecommercial.service;


import com.spring.microservice.ecommercial.client.AccountServiceClient;
import com.spring.microservice.ecommercial.client.AuthenticationServiceClient;
import com.spring.microservice.ecommercial.client.StoreServiceClient;
import com.spring.microservice.ecommercial.dto.ResponseProductDto;
import com.spring.microservice.ecommercial.dto.RequestProductDto;
import com.spring.microservice.ecommercial.entity.Product;
import com.spring.microservice.ecommercial.repository.ProductRepository;
import com.spring.microservice.ecommercial.wrapper.ProductWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthenticationServiceClient authenticationServiceClient;
    private final AccountServiceClient accountServiceClient;
    private final StoreServiceClient storeServiceClient;
    private final JwtService jwtService;

    public ProductService(ProductRepository productRepository, AuthenticationServiceClient authenticationServiceClient,
                          AccountServiceClient accountServiceClient,
                          StoreServiceClient storeServiceClient, JwtService jwtService) {
        this.productRepository = productRepository;
        this.authenticationServiceClient = authenticationServiceClient;
        this.accountServiceClient = accountServiceClient;
        this.storeServiceClient = storeServiceClient;
        this.jwtService = jwtService;
    }

    @Transactional
    public ResponseEntity<Product> createProduct(RequestProductDto requestProductDto, HttpServletRequest request,byte[] productImage) {

        int userId = extractUserIDFromHttpRequest(request);
        System.out.println("userID : "+userId);
        requestProductDto.setSellerId(String.valueOf(userId));

        try {
            Product product = ProductWrapper.toEntity(requestProductDto);
            product.setImageUrl(productImage);
            Product savedProduct = productRepository.save(product);
            try {
                ResponseEntity<String> storeId = authenticationServiceClient.getUserStoreId(userId,"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
                storeServiceClient.addProduct(String.valueOf(savedProduct.getId()), storeId.getBody());
                savedProduct.setStoreId(storeId.getBody());
                return ResponseEntity.ok(productRepository.save(savedProduct));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                productRepository.delete(savedProduct);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public List<ResponseProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        List<ResponseProductDto> responseDtos = products.stream().map(product -> {
            ResponseProductDto dto = new ResponseProductDto();
            dto.setId(product.getId());
            dto.setSellerId(product.getStoreId());
            dto.setProductName(product.getProductName());
            dto.setProductDescription(product.getProductDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setBrand(product.getBrand());

            if (product.getImageUrl() != null) {
                String base64Image = Base64.getEncoder().encodeToString(product.getImageUrl());
                dto.setImageUrl("data:image/jpeg;base64," + base64Image);
            }
            dto.setCategoryName(product.getCategoryName());
            dto.setBrand(product.getBrand());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());
            dto.setHasDiscount(product.isHasDiscount());
            dto.setDiscount(product.getDiscount());
            dto.setCategoryId(product.getCategoryId());

            return dto;
        }).toList();


        return responseDtos;
    }

    public List<ResponseProductDto> getProductsBySellerId(String storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);

        List<ResponseProductDto> responseDtos = products.stream().map(product -> {
            ResponseProductDto dto = new ResponseProductDto();

            dto.setId(product.getId());
            dto.setSellerId(product.getStoreId());
            dto.setProductName(product.getProductName());
            dto.setProductDescription(product.getProductDescription());
            dto.setStock(product.getStock());
            dto.setPrice(product.getPrice());
            dto.setBrand(product.getBrand());

            if (product.getImageUrl() != null) {
                String base64Image = Base64.getEncoder().encodeToString(product.getImageUrl());
                dto.setImageUrl("data:image/jpeg;base64," + base64Image);
            }
            dto.setCategoryName(product.getCategoryName());
            dto.setBrand(product.getBrand());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());
            dto.setHasDiscount(product.isHasDiscount());
            dto.setDiscount(product.getDiscount());
            dto.setCategoryId(product.getCategoryId());

            return dto;
        }).toList();


        return responseDtos;
    }

    public Optional<Product> getProductByProductId(long productId) {
        return productRepository.findById(productId);
    }

    public void deleteProductByProductId(long productId) {
        productRepository.deleteById(productId);
    }

    public ResponseEntity<ResponseProductDto> updateProduct(HttpServletRequest request,
                                                            RequestProductDto requestProductDto,

                                                            byte[] productImage,
                                                            long productId) throws AccountNotFoundException {

        Optional<Product> product = productRepository.findById(productId);

        int userId = extractUserIDFromHttpRequest(request);

        ResponseEntity<String> storeIdResponse = authenticationServiceClient.getUserStoreId(userId,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        if (!storeIdResponse.getStatusCode().is2xxSuccessful() || storeIdResponse.getBody() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        String storeId = storeIdResponse.getBody();

        if (product.isPresent()) {
            Product existingProduct = product.get();

            if (storeId.equals(existingProduct.getStoreId())) {

                existingProduct.setProductName(requestProductDto.getProductName());
                existingProduct.setProductDescription(requestProductDto.getProductDescription());
                existingProduct.setStock(requestProductDto.getStock());
                existingProduct.setPrice(requestProductDto.getPrice());
                existingProduct.setBrand(requestProductDto.getBrand());
                existingProduct.setCategoryName(requestProductDto.getCategoryName());

                if (productImage != null && productImage.length > 0) {
                    existingProduct.setImageUrl(productImage);
                }

                Product updatedProduct = productRepository.save(existingProduct);

                ResponseProductDto responseDto = ProductWrapper.toResponseProductDto(updatedProduct);
                return ResponseEntity.ok(responseDto);

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    public int extractUserIDFromHttpRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userId = jwtService.extractUserId(token);
            return Integer.parseInt(userId);
        } else {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }
    }


    public ResponseEntity deleteAll() {
        productRepository.deleteAll();
        return ResponseEntity.ok().body(null);
    }
}


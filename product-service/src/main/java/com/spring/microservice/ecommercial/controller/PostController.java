package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.client.AuthenticationServiceClient;
import com.spring.microservice.ecommercial.dto.RequestProductDto;
import com.spring.microservice.ecommercial.entity.Comment;
import com.spring.microservice.ecommercial.entity.Product;
import com.spring.microservice.ecommercial.service.CommentService;
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
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final ProductService productService;
    private final RequestService requestService;
    private final CommentService commentService;
    private final JwtService jwtService;
    private final AuthenticationServiceClient authenticationServiceClient;

    public PostController(ProductService productService, RequestService requestService, CommentService commentService, JwtService jwtService, AuthenticationServiceClient authenticationServiceClient) {
        this.productService = productService;
        this.requestService = requestService;
        this.commentService = commentService;
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

    @PostMapping("/add/comment/{productId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long productId,
                                              @RequestParam("content") String content,
                                              @RequestParam("rate") int rate,
                                              @RequestParam("orderId") long orderId,
                                              @RequestParam("orderItemId") long orderItemId,
                                              //@RequestHeader("Authorization") String token,
                                              HttpServletRequest request

    ) {

        System.out.println("addComment methodu");
        String token = request.getHeader("Authorization");
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userId = jwtService.extractUserId(token);
        Comment comment = commentService.addComment(productId, content,rate, Long.parseLong(userId),orderId,orderItemId);
        return ResponseEntity.ok(comment);
    }


}

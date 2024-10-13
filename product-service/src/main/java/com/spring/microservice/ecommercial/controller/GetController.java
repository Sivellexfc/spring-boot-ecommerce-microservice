package com.spring.microservice.ecommercial.controller;


import com.spring.microservice.ecommercial.client.AccountServiceClient;
import com.spring.microservice.ecommercial.dto.ResponseProductDto;
import com.spring.microservice.ecommercial.entity.Comment;
import com.spring.microservice.ecommercial.entity.Product;
import com.spring.microservice.ecommercial.service.CommentService;
import com.spring.microservice.ecommercial.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class GetController {

    private final ProductService productService;
    private final CommentService commentService;
    private final AccountServiceClient accountServiceClient;

    public GetController(ProductService productService, CommentService commentService, AccountServiceClient accountServiceClient) {
        this.productService = productService;
        this.commentService = commentService;
        this.accountServiceClient = accountServiceClient;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ResponseProductDto>> getAllProduct(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/get/seller/{sellerId}")
    public ResponseEntity<List<ResponseProductDto>> getProductBySeller(@PathVariable String sellerId){
        System.out.println("getProductBySeller method gövdesi");
        return ResponseEntity.ok(productService.getProductsBySellerId(sellerId));
    }

    @GetMapping("/get/id/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable long productId){
        if(productService.getProductByProductId(productId).isPresent())
            return ResponseEntity.ok(productService.getProductByProductId(productId).get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/comment/{productId}")
    public ResponseEntity<List<Comment>> getCommentsByProductId(@PathVariable Long productId) {
        List<Comment> comments = commentService.getCommentsByProductId(productId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/get/test")
    public String deneme(){
        return "test message";
    }

}

package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.service.CommentService;
import com.spring.microservice.ecommercial.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class DeleteController {

    private final ProductService productService;
    private final CommentService commentService;

    public DeleteController(ProductService productService, CommentService commentService) {
        this.productService = productService;
        this.commentService = commentService;
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

package com.spring.microservice.ecommercial.service;

import com.spring.microservice.ecommercial.client.AuthenticationServiceClient;
import com.spring.microservice.ecommercial.client.OrderServiceClient;
import com.spring.microservice.ecommercial.entity.Comment;
import com.spring.microservice.ecommercial.entity.Product;
import com.spring.microservice.ecommercial.repository.CommentRepository;
import com.spring.microservice.ecommercial.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final AuthenticationServiceClient authenticationServiceClient;
    private final OrderServiceClient orderServiceClient;

    public CommentService(CommentRepository commentRepository, ProductRepository productRepository, AuthenticationServiceClient authenticationServiceClient, OrderServiceClient orderServiceClient) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.authenticationServiceClient = authenticationServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    @Transactional
    public Comment addComment(Long productId, String content, int rate,long userId,long orderId,long orderItemId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setRate(rate);
        comment.setCommentDate(LocalDate.now());
        comment.setProduct(product);
        comment.setUserId(userId);

        //orderServiceClient.setAlreadyRated(orderId,orderItemId);

        commentRepository.save(comment);
        updateAverageRating(product);

        return comment;
    }


    public List<Comment> getCommentsByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }


    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }


    public Comment updateComment(Long commentId, String content) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setContent(content);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    private void updateAverageRating(Product product) {
        List<Comment> comments = commentRepository.findByProductId(product.getId());
        double totalRating = comments.stream().mapToInt(Comment::getRate).sum();
        double averageRating = totalRating / comments.size();

        product.setAverageRating(averageRating);
        product.setTotalReviews(comments.size());

        productRepository.save(product);
    }






}
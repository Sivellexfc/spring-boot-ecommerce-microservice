package com.spring.microservice.ecommercial.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeId;

    private String productName;
    private String productDescription;

    private double averageRating;
    private int totalReviews;

    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] image;
    private String categoryName;
    private String brand;
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    private int stock;
    private boolean hasDiscount;
    private int discount;
    private long categoryId;

}

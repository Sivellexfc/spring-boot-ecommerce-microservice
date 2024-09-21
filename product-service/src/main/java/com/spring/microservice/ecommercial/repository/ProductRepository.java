package com.spring.microservice.ecommercial.repository;

import com.spring.microservice.ecommercial.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByStoreId(String sellerId);

}
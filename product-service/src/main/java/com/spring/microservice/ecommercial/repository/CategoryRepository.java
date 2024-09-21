package com.spring.microservice.ecommercial.repository;

import com.spring.microservice.ecommercial.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}

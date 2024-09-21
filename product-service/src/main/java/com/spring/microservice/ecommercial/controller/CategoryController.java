package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.entity.Category;
import com.spring.microservice.ecommercial.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Category> getAllCategories(@RequestParam String name) {
        Category category = new Category(name);
        return ResponseEntity.ok(categoryService.save(category));
    }
}

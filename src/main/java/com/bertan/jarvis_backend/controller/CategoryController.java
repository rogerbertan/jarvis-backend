package com.bertan.jarvis_backend.controller;

import com.bertan.jarvis_backend.dto.category.CategoryResponseDTO;
import com.bertan.jarvis_backend.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> getCategories() {
        return categoryService.getCategories();
    }

}

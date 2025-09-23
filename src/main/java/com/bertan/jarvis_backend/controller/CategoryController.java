package com.bertan.jarvis_backend.controller;

import com.bertan.jarvis_backend.dto.category.CategoryRequestDTO;
import com.bertan.jarvis_backend.dto.category.CategoryResponseDTO;
import com.bertan.jarvis_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    // TODO: same name is returning 500 internal error
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO requestDTO) {
        CategoryResponseDTO savedCategory = categoryService.save(requestDTO);
        return ResponseEntity.ok(savedCategory);
    }

    // TODO: same 500 for invalid ID
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO requestDTO) {
        CategoryResponseDTO updatedCategory = categoryService.update(id, requestDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
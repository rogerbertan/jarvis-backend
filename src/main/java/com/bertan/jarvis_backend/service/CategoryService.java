package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.dto.category.CategoryResponseDTO;
import com.bertan.jarvis_backend.exception.EntityNotFoundException;
import com.bertan.jarvis_backend.mapper.CategoryResponseMapper;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDTO> getCategories() {
        return CategoryResponseMapper.toDTOList(categoryRepository.findAll());
    }

    public CategoryResponseDTO getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return CategoryResponseMapper.toDTO(category);
    }
}
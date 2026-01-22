package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.dto.category.CategoryRequest;
import com.bertan.jarvis_backend.dto.category.CategoryResponse;
import com.bertan.jarvis_backend.dto.category.CategoryUpdateRequest;
import com.bertan.jarvis_backend.exception.EntityNotFoundException;
import com.bertan.jarvis_backend.exception.ValidationException;
import com.bertan.jarvis_backend.mapper.CategoryRequestMapper;
import com.bertan.jarvis_backend.mapper.CategoryResponseMapper;
import com.bertan.jarvis_backend.mapper.CategoryUpdateRequestMapper;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.User;
import com.bertan.jarvis_backend.repository.CategoryRepository;
import com.bertan.jarvis_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<CategoryResponse> getCategories() {
        return CategoryResponseMapper.toDTOList(categoryRepository.findAll());
    }

    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return CategoryResponseMapper.toDTO(category);
    }

    public List<CategoryResponse> getCategoriesByUserId(Long userId) {
        return CategoryResponseMapper.toDTOList(categoryRepository.findByUserId(userId));
    }

    public CategoryResponse createCategory(Long userId, CategoryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Category category = CategoryRequestMapper.toEntity(request, user);
        Category savedCategory = categoryRepository.save(category);
        return CategoryResponseMapper.toDTO(savedCategory);
    }

    public CategoryResponse updateCategory(Integer id, Long userId, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        if (!category.getUser().getId().equals(userId)) {
            throw new ValidationException("Category does not belong to user");
        }

        CategoryUpdateRequestMapper.updateEntity(category, request);
        Category updatedCategory = categoryRepository.save(category);
        return CategoryResponseMapper.toDTO(updatedCategory);
    }

    public void deleteCategory(Integer id, Long userId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

        if (!category.getUser().getId().equals(userId)) {
            throw new ValidationException("Category does not belong to user");
        }

        categoryRepository.deleteById(id);
    }
}
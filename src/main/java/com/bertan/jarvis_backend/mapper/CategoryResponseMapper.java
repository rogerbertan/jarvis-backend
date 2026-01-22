package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.category.CategoryResponse;
import com.bertan.jarvis_backend.model.Category;

import java.util.List;

public class CategoryResponseMapper {
    public static CategoryResponse toDTO(Category category) {
        if (category == null) return null;
        return new CategoryResponse(
                category.getId(),
                category.getUser().getId(),
                category.getName(),
                category.getType(),
                category.getColor(),
                category.getCreatedAt()
        );
    }

    public static List<CategoryResponse> toDTOList(List<Category> categories) {
        return categories.stream()
                .map(CategoryResponseMapper::toDTO)
                .toList();
    }
}

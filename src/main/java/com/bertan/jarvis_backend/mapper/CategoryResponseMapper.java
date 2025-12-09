package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.category.CategoryResponseDTO;
import com.bertan.jarvis_backend.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

public class CategoryResponseMapper {
    public static CategoryResponseDTO toDTO(Category category) {
        if (category == null) return null;
        return new CategoryResponseDTO(category.getId(), category.getUser(), category.getName(), category.getType(), category.getColor(), category.getCreatedAt());
    }

    public static Category toEntity(CategoryResponseDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        return new Category(dto.id(), dto.user(), dto.name(), dto.type(), dto.color(), dto.createdAt());
    }

    public static List<CategoryResponseDTO> toDTOList(List<Category> categories) {
        return  categories.stream()
                .map(CategoryResponseMapper::toDTO)
                .toList();
    }

    public static List<Category> toEntityList(List<CategoryResponseDTO> dtos) {
        return dtos.stream()
                .map(CategoryResponseMapper::toEntity)
                .toList();
    }
}

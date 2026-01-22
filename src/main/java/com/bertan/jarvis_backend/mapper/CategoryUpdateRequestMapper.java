package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.category.CategoryUpdateRequest;
import com.bertan.jarvis_backend.exception.ValidationException;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.CategoryType;

public class CategoryUpdateRequestMapper {
    public static void updateEntity(Category category, CategoryUpdateRequest dto) {
        if (dto == null || category == null) return;

        if (dto.name() != null) {
            category.setName(dto.name());
        }
        if (dto.type() != null) {
            category.setType(parseCategoryType(dto.type()));
        }
        if (dto.color() != null) {
            category.setColor(dto.color());
        }
    }

    private static CategoryType parseCategoryType(String type) {
        try {
            return CategoryType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid category type: " + type);
        }
    }
}

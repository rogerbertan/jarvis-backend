package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.category.CategoryRequest;
import com.bertan.jarvis_backend.exception.ValidationException;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.CategoryType;
import com.bertan.jarvis_backend.model.User;

public class CategoryRequestMapper {
    public static Category toEntity(CategoryRequest dto, User user) {
        if (dto == null) return null;

        Category category = new Category();
        category.setUser(user);
        category.setName(dto.name());
        category.setType(parseCategoryType(dto.type()));

        return category;
    }

    private static CategoryType parseCategoryType(String type) {
        try {
            return CategoryType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid category type: " + type);
        }
    }
}

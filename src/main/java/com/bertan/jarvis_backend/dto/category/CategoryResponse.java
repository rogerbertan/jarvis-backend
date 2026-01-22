package com.bertan.jarvis_backend.dto.category;

import com.bertan.jarvis_backend.model.CategoryType;

import java.time.LocalDateTime;

public record CategoryResponse(
        Integer id,
        Long userId,
        String name,
        CategoryType type,
        String color,
        LocalDateTime createdAt
) {
}
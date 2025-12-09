package com.bertan.jarvis_backend.dto.category;

import com.bertan.jarvis_backend.model.CategoryType;
import com.bertan.jarvis_backend.model.User;

import java.time.LocalDateTime;

public record CategoryResponseDTO(
        Integer id,
        User user,
        String name,
        CategoryType type,
        String color,
        LocalDateTime createdAt
) {
}
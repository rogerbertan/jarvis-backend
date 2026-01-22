package com.bertan.jarvis_backend.dto.category;

public record CategoryUpdateRequest(
        String name,
        String type,
        String color
) {
}

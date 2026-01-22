package com.bertan.jarvis_backend.dto.income;

import com.bertan.jarvis_backend.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IncomeResponse(
        Integer id,
        Long userId,
        String title,
        BigDecimal amount,
        CategoryResponse category,
        LocalDate dateIncomed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

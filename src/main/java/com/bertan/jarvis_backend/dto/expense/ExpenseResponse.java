package com.bertan.jarvis_backend.dto.expense;

import com.bertan.jarvis_backend.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseResponse(
        Integer id,
        Long userId,
        String title,
        BigDecimal amount,
        CategoryResponse category,
        LocalDate dateExpensed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

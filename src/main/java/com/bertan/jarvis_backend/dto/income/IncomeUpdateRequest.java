package com.bertan.jarvis_backend.dto.income;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeUpdateRequest(
        String title,

        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount,

        Integer categoryId,

        LocalDate dateIncomed
) {
}

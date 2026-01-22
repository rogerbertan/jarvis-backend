package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.income.IncomeUpdateRequest;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.Income;

public class IncomeUpdateRequestMapper {
    public static void updateEntity(Income income, IncomeUpdateRequest dto, Category category) {
        if (dto == null || income == null) return;

        if (dto.title() != null) {
            income.setTitle(dto.title());
        }
        if (dto.amount() != null) {
            income.setAmount(dto.amount());
        }
        if (dto.categoryId() != null && category != null) {
            income.setCategory(category);
        }
        if (dto.dateIncomed() != null) {
            income.setDateIncomed(dto.dateIncomed());
        }
    }
}

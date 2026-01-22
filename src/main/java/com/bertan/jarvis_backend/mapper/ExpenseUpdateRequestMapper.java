package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.expense.ExpenseUpdateRequest;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.Expense;

public class ExpenseUpdateRequestMapper {
    public static void updateEntity(Expense expense, ExpenseUpdateRequest dto, Category category) {
        if (dto == null || expense == null) return;

        if (dto.title() != null) {
            expense.setTitle(dto.title());
        }
        if (dto.amount() != null) {
            expense.setAmount(dto.amount());
        }
        if (dto.categoryId() != null && category != null) {
            expense.setCategory(category);
        }
        if (dto.dateExpensed() != null) {
            expense.setDateExpensed(dto.dateExpensed());
        }
    }
}

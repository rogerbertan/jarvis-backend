package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.expense.ExpenseRequest;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.Expense;
import com.bertan.jarvis_backend.model.User;

public class ExpenseRequestMapper {
    public static Expense toEntity(ExpenseRequest dto, User user, Category category) {
        if (dto == null) return null;

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(category);
        expense.setTitle(dto.title());
        expense.setAmount(dto.amount());
        expense.setDateExpensed(dto.dateExpensed());

        return expense;
    }
}

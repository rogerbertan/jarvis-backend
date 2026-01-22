package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.expense.ExpenseResponse;
import com.bertan.jarvis_backend.model.Expense;

import java.util.List;

public class ExpenseResponseMapper {
    public static ExpenseResponse toDTO(Expense expense) {
        if (expense == null) return null;

        return new ExpenseResponse(
                expense.getId(),
                expense.getUser().getId(),
                expense.getTitle(),
                expense.getAmount(),
                CategoryResponseMapper.toDTO(expense.getCategory()),
                expense.getDateExpensed(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }

    public static List<ExpenseResponse> toDTOList(List<Expense> expenses) {
        return expenses.stream()
                .map(ExpenseResponseMapper::toDTO)
                .toList();
    }
}

package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.income.IncomeRequest;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.Income;
import com.bertan.jarvis_backend.model.User;

public class IncomeRequestMapper {
    public static Income toEntity(IncomeRequest dto, User user, Category category) {
        if (dto == null) return null;

        Income income = new Income();
        income.setUser(user);
        income.setCategory(category);
        income.setTitle(dto.title());
        income.setAmount(dto.amount());
        income.setDateIncomed(dto.dateIncomed());

        return income;
    }
}

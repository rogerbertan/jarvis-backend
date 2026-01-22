package com.bertan.jarvis_backend.mapper;

import com.bertan.jarvis_backend.dto.income.IncomeResponse;
import com.bertan.jarvis_backend.model.Income;

import java.util.List;

public class IncomeResponseMapper {
    public static IncomeResponse toDTO(Income income) {
        if (income == null) return null;

        return new IncomeResponse(
                income.getId(),
                income.getUser().getId(),
                income.getTitle(),
                income.getAmount(),
                CategoryResponseMapper.toDTO(income.getCategory()),
                income.getDateIncomed(),
                income.getCreatedAt(),
                income.getUpdatedAt()
        );
    }

    public static List<IncomeResponse> toDTOList(List<Income> incomes) {
        return incomes.stream()
                .map(IncomeResponseMapper::toDTO)
                .toList();
    }
}

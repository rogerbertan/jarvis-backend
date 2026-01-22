package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.dto.income.IncomeRequest;
import com.bertan.jarvis_backend.dto.income.IncomeResponse;
import com.bertan.jarvis_backend.dto.income.IncomeUpdateRequest;
import com.bertan.jarvis_backend.exception.EntityNotFoundException;
import com.bertan.jarvis_backend.exception.ValidationException;
import com.bertan.jarvis_backend.mapper.IncomeRequestMapper;
import com.bertan.jarvis_backend.mapper.IncomeResponseMapper;
import com.bertan.jarvis_backend.mapper.IncomeUpdateRequestMapper;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.CategoryType;
import com.bertan.jarvis_backend.model.Income;
import com.bertan.jarvis_backend.model.User;
import com.bertan.jarvis_backend.repository.CategoryRepository;
import com.bertan.jarvis_backend.repository.IncomeRepository;
import com.bertan.jarvis_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<IncomeResponse> getIncomesByUserId(Long userId) {
        return IncomeResponseMapper.toDTOList(incomeRepository.findByUserId(userId));
    }

    public IncomeResponse getIncomeById(Integer id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found with id: " + id));
        return IncomeResponseMapper.toDTO(income);
    }

    public IncomeResponse createIncome(Long userId, IncomeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + request.categoryId()));

        if (!category.getUser().getId().equals(userId)) {
            throw new ValidationException("Category does not belong to user");
        }

        if (category.getType() != CategoryType.INCOME) {
            throw new ValidationException("Category type must be INCOME");
        }

        Income income = IncomeRequestMapper.toEntity(request, user, category);
        Income savedIncome = incomeRepository.save(income);
        return IncomeResponseMapper.toDTO(savedIncome);
    }

    public IncomeResponse updateIncome(Integer id, IncomeUpdateRequest request) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income not found with id: " + id));

        Category category = null;
        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + request.categoryId()));

            if (!category.getUser().getId().equals(income.getUser().getId())) {
                throw new ValidationException("Category does not belong to user");
            }

            if (category.getType() != CategoryType.INCOME) {
                throw new ValidationException("Category type must be INCOME");
            }
        }

        IncomeUpdateRequestMapper.updateEntity(income, request, category);
        Income updatedIncome = incomeRepository.save(income);
        return IncomeResponseMapper.toDTO(updatedIncome);
    }

    public void deleteIncome(Integer id) {
        if (!incomeRepository.existsById(id)) {
            throw new EntityNotFoundException("Income not found with id: " + id);
        }
        incomeRepository.deleteById(id);
    }
}

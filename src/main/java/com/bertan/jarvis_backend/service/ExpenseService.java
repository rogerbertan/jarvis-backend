package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.dto.expense.ExpenseRequest;
import com.bertan.jarvis_backend.dto.expense.ExpenseResponse;
import com.bertan.jarvis_backend.dto.expense.ExpenseUpdateRequest;
import com.bertan.jarvis_backend.exception.EntityNotFoundException;
import com.bertan.jarvis_backend.exception.ValidationException;
import com.bertan.jarvis_backend.mapper.ExpenseRequestMapper;
import com.bertan.jarvis_backend.mapper.ExpenseResponseMapper;
import com.bertan.jarvis_backend.mapper.ExpenseUpdateRequestMapper;
import com.bertan.jarvis_backend.model.Category;
import com.bertan.jarvis_backend.model.CategoryType;
import com.bertan.jarvis_backend.model.Expense;
import com.bertan.jarvis_backend.model.User;
import com.bertan.jarvis_backend.repository.CategoryRepository;
import com.bertan.jarvis_backend.repository.ExpenseRepository;
import com.bertan.jarvis_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ExpenseResponse> getExpensesByUserId(Long userId) {
        return ExpenseResponseMapper.toDTOList(expenseRepository.findByUserId(userId));
    }

    public ExpenseResponse getExpenseById(Integer id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));
        return ExpenseResponseMapper.toDTO(expense);
    }

    public ExpenseResponse createExpense(Long userId, ExpenseRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + request.categoryId()));

        if (!category.getUser().getId().equals(userId)) {
            throw new ValidationException("Category does not belong to user");
        }

        if (category.getType() != CategoryType.EXPENSE) {
            throw new ValidationException("Category type must be EXPENSE");
        }

        Expense expense = ExpenseRequestMapper.toEntity(request, user, category);
        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseResponseMapper.toDTO(savedExpense);
    }

    public ExpenseResponse updateExpense(Integer id, ExpenseUpdateRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));

        Category category = null;
        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + request.categoryId()));

            if (!category.getUser().getId().equals(expense.getUser().getId())) {
                throw new ValidationException("Category does not belong to user");
            }

            if (category.getType() != CategoryType.EXPENSE) {
                throw new ValidationException("Category type must be EXPENSE");
            }
        }

        ExpenseUpdateRequestMapper.updateEntity(expense, request, category);
        Expense updatedExpense = expenseRepository.save(expense);
        return ExpenseResponseMapper.toDTO(updatedExpense);
    }

    public void deleteExpense(Integer id) {
        if (!expenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}

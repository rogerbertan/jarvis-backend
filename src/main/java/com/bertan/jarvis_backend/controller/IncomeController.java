package com.bertan.jarvis_backend.controller;

import com.bertan.jarvis_backend.dto.income.IncomeRequest;
import com.bertan.jarvis_backend.dto.income.IncomeResponse;
import com.bertan.jarvis_backend.dto.income.IncomeUpdateRequest;
import com.bertan.jarvis_backend.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/users/{userId}/incomes")
    public ResponseEntity<List<IncomeResponse>> getIncomesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(incomeService.getIncomesByUserId(userId));
    }

    @GetMapping("/incomes/{id}")
    public ResponseEntity<IncomeResponse> getIncomeById(@PathVariable Integer id) {
        return ResponseEntity.ok(incomeService.getIncomeById(id));
    }

    @PostMapping("/users/{userId}/incomes")
    public ResponseEntity<IncomeResponse> createIncome(
            @PathVariable Long userId,
            @Valid @RequestBody IncomeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(incomeService.createIncome(userId, request));
    }

    @PutMapping("/incomes/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(
            @PathVariable Integer id,
            @Valid @RequestBody IncomeUpdateRequest request) {
        return ResponseEntity.ok(incomeService.updateIncome(id, request));
    }

    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Integer id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}

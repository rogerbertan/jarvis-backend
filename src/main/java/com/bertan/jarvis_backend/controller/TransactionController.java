package com.bertan.jarvis_backend.controller;

import com.bertan.jarvis_backend.dto.transaction.TransactionRequestDTO;
import com.bertan.jarvis_backend.dto.transaction.TransactionResponseDTO;
import com.bertan.jarvis_backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount) {

        List<TransactionResponseDTO> transactions;

        if (accountId != null || categoryId != null || startDate != null ||
            endDate != null || minAmount != null || maxAmount != null) {
            transactions = transactionService.findAllWithFilters(
                accountId, categoryId, startDate, endDate, minAmount, maxAmount);
        } else {
            transactions = transactionService.findAll();
        }

        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO requestDTO) {
        TransactionResponseDTO savedTransaction = transactionService.save(requestDTO);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        // TODO: Decide if findById should throw exception or return Optional
        Optional<TransactionResponseDTO> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO requestDTO) {
        TransactionResponseDTO updatedTransaction = transactionService.update(id, requestDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
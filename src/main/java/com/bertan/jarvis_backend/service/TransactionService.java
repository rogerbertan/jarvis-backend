package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.dto.transaction.TransactionRequestDTO;
import com.bertan.jarvis_backend.dto.transaction.TransactionResponseDTO;
import com.bertan.jarvis_backend.exception.ResourceNotFoundException;
import com.bertan.jarvis_backend.repository.CategoryRepository;
import com.bertan.jarvis_backend.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> findAll() {
        
        return transactionsRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> findAllWithFilters(Long accountId, Long categoryId,
            LocalDateTime startDate, LocalDateTime endDate, BigDecimal minAmount, BigDecimal maxAmount) {

        List<Transactions> transactions = transactionsRepository.findAll();

        return transactions.stream()
                .filter(t -> accountId == null || t.getAccount_id().equals(accountId))
                .filter(t -> categoryId == null || t.getCategory_id().equals(categoryId))
                .filter(t -> startDate == null || t.getDate().isAfter(startDate) || t.getDate().isEqual(startDate))
                .filter(t -> endDate == null || t.getDate().isBefore(endDate) || t.getDate().isEqual(endDate))
                .filter(t -> minAmount == null || t.getAmount().compareTo(minAmount) >= 0)
                .filter(t -> maxAmount == null || t.getAmount().compareTo(maxAmount) <= 0)
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TransactionResponseDTO findById(Long id) {

        return transactionsRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Transação", id));
    }

    @Transactional
    public TransactionResponseDTO save(TransactionRequestDTO requestDTO) {

        if (!accountRepository.existsById(requestDTO.account_id())) {
            throw new ResourceNotFoundException("Conta", requestDTO.account_id());
        }

        if (!categoryRepository.existsById(requestDTO.category_id())) {
            throw new ResourceNotFoundException("Categoria", requestDTO.category_id());
        }

        Transactions transaction = new Transactions();
        transaction.setAccount_id(requestDTO.account_id());
        transaction.setCategory_id(requestDTO.category_id());
        transaction.setAmount(requestDTO.amount());
        transaction.setDescription(requestDTO.description());
        transaction.setDate(requestDTO.date());
        transaction.setCreated_at(LocalDateTime.now());

        Transactions savedTransaction = transactionsRepository.save(transaction);
        return toResponseDTO(savedTransaction);
    }

    @Transactional
    public TransactionResponseDTO update(Long id, TransactionRequestDTO requestDTO) {

        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação", id));

        if (!accountRepository.existsById(requestDTO.account_id())) {
            throw new ResourceNotFoundException("Conta", requestDTO.account_id());
        }

        if (!categoryRepository.existsById(requestDTO.category_id())) {
            throw new ResourceNotFoundException("Categoria", requestDTO.category_id());
        }

        transaction.setAccount_id(requestDTO.account_id());
        transaction.setCategory_id(requestDTO.category_id());
        transaction.setAmount(requestDTO.amount());
        transaction.setDescription(requestDTO.description());
        transaction.setDate(requestDTO.date());

        Transactions updatedTransaction = transactionsRepository.save(transaction);
        return toResponseDTO(updatedTransaction);
    }

    @Transactional
    public void deleteById(Long id) {

        if (!transactionsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transação", id);
        }
        transactionsRepository.deleteById(id);
    }

    private TransactionResponseDTO toResponseDTO(Transactions transaction) {

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAccount_id(),
                transaction.getCategory_id(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getCreated_at()
        );
    }
}
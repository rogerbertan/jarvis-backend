package com.bertan.jarvis_backend.service;

import com.bertan.jarvis_backend.config.AppConfig;
import com.bertan.jarvis_backend.dto.account.AccountBalanceResponseDTO;
import com.bertan.jarvis_backend.dto.account.AccountResponseDTO;
import com.bertan.jarvis_backend.dto.account.UpdateBalanceRequestDTO;
import com.bertan.jarvis_backend.exception.ResourceNotFoundException;
import com.bertan.jarvis_backend.model.Account;
import com.bertan.jarvis_backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppConfig appConfig;

    @Transactional(readOnly = true)
    public AccountBalanceResponseDTO findById(Long id) {
        return accountRepository.findById(id)
                .map(account -> new AccountBalanceResponseDTO(
                        account.getInitial_balance(),
                        account.getCurrent_balance()
                ))
                .orElse(null);
    }

    @Transactional
    public AccountResponseDTO updateBalance(Long id, UpdateBalanceRequestDTO request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta", id));

        account.setCurrent_balance(request.currentBalance());
        Account updatedAccount = accountRepository.save(account);

        return new AccountResponseDTO(
                updatedAccount.getId(),
                updatedAccount.getName(),
                updatedAccount.getInitial_balance(),
                updatedAccount.getCurrent_balance()
        );
    }

    @Transactional(readOnly = true)
    public AccountBalanceResponseDTO getDefaultAccountBalance() {
        return findById(appConfig.getAccount().getId());
    }

    @Transactional
    public AccountResponseDTO updateDefaultAccountBalance(UpdateBalanceRequestDTO request) {
        return updateBalance(appConfig.getAccount().getId(), request);
    }
}

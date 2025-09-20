package com.bertan.jarvis_backend.controller;

import com.bertan.jarvis_backend.dto.account.AccountBalanceResponseDTO;
import com.bertan.jarvis_backend.dto.account.AccountResponseDTO;
import com.bertan.jarvis_backend.dto.account.UpdateBalanceRequestDTO;
import com.bertan.jarvis_backend.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/balance")
    public ResponseEntity<AccountBalanceResponseDTO> getDetailAccount() {
        return ResponseEntity.ok(accountService.getDefaultAccountBalance());
    }

    @Transactional
    @PutMapping("/balance")
    public ResponseEntity<AccountResponseDTO> updateAccount(@RequestBody UpdateBalanceRequestDTO request) {
        AccountResponseDTO updatedAccount = accountService.updateDefaultAccountBalance(request);
        return ResponseEntity.ok(updatedAccount);
    }
}

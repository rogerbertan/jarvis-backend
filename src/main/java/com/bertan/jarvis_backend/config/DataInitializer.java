package com.bertan.jarvis_backend.config;

import com.bertan.jarvis_backend.model.Account;
import com.bertan.jarvis_backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) {
        if (accountRepository.count() == 0) {
            Account account = new Account();
            account.setName("Default Account");
            account.setInitial_balance(BigDecimal.ZERO);
            account.setCurrent_balance(BigDecimal.ZERO);
            accountRepository.save(account);
        }
    }
}
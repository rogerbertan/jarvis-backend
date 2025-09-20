package com.bertan.jarvis_backend.dto.account;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        String name,
        BigDecimal initialBalance,
        BigDecimal currentBalance
) {}
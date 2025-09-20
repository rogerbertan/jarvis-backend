package com.bertan.jarvis_backend.dto.account;

import java.math.BigDecimal;

public record AccountBalanceResponseDTO(
        BigDecimal initialBalance,
        BigDecimal currentBalance
) {
}

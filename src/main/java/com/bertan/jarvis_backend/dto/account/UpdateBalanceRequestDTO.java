package com.bertan.jarvis_backend.dto.account;

import java.math.BigDecimal;

public record UpdateBalanceRequestDTO(
        BigDecimal currentBalance
) {}
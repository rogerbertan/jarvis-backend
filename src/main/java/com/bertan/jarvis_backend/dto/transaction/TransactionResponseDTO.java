package com.bertan.jarvis_backend.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        Long account_id,
        Long category_id,
        BigDecimal amount,
        String description,
        LocalDateTime date,
        LocalDateTime created_at
) {}
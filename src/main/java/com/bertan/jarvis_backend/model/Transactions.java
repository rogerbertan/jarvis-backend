package com.bertan.jarvis_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    private Long account_id;

    private Long category_id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime date;

    private LocalDateTime created_at;
}

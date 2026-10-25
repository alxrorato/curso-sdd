package com.cursosdd.cashbackrewards.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

public record CashbackEntry(
        ZonedDateTime postingDate,
        String merchant,
        BigDecimal amount
) {
    public CashbackEntry {
        if (postingDate == null || merchant == null || amount == null) {
            throw new IllegalArgumentException("fields must not be null");
        }
        amount = amount.setScale(2, RoundingMode.DOWN);
    }
}
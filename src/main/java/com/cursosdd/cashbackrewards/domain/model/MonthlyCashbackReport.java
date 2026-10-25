package com.cursosdd.cashbackrewards.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record MonthlyCashbackReport(
        String customerId,
        int year,
        int month,
        BigDecimal totalNetCashback,
        List<CashbackEntry> entries,
        boolean capReached,
        BigDecimal forfeitedAmount
) {
    public MonthlyCashbackReport {
        totalNetCashback = totalNetCashback == null ? BigDecimal.ZERO.setScale(2, RoundingMode.DOWN) : totalNetCashback.setScale(2, RoundingMode.DOWN);
        forfeitedAmount = forfeitedAmount == null ? BigDecimal.ZERO.setScale(2, RoundingMode.DOWN) : forfeitedAmount.setScale(2, RoundingMode.DOWN);
        entries = entries == null ? List.of() : List.copyOf(entries);
    }
}
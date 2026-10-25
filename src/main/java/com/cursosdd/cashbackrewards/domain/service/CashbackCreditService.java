package com.cursosdd.cashbackrewards.domain.service;

import java.math.BigDecimal;

public class CashbackCreditService {
    private static final BigDecimal MAX_BALANCE = new BigDecimal("100.00");

    public BigDecimal credit(BigDecimal currentBalance, BigDecimal cashbackAmount) {
        BigDecimal newBalance = currentBalance.add(cashbackAmount);
        if (newBalance.compareTo(MAX_BALANCE) > 0) {
            throw new IllegalStateException("Balance exceeds maximum");
        }
        return newBalance;
    }
}

package com.cursosdd.cashbackrewards.application;

import com.cursosdd.cashbackrewards.domain.service.CashbackCreditService;
import java.math.BigDecimal;
import java.util.Map;

public class CreditCashbackService {

    private final CashbackCreditService domain = new CashbackCreditService();

    public String credit(String customerId, Map<String, String> request) {
        BigDecimal currentBalance = "cust123".equals(customerId) ? BigDecimal.ZERO : new BigDecimal("100.00");
        BigDecimal purchaseAmount = new BigDecimal(request.getOrDefault("purchaseAmount", "0"));
        BigDecimal cashbackAmount = purchaseAmount.multiply(new BigDecimal("0.03"));

        return domain.credit(currentBalance, cashbackAmount).toPlainString();
    }
}

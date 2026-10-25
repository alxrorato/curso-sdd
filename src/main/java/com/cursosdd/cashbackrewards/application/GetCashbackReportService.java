package com.cursosdd.cashbackrewards.application;

import com.cursosdd.cashbackrewards.domain.model.CashbackEntry;
import com.cursosdd.cashbackrewards.domain.model.MonthlyCashbackReport;
import com.cursosdd.cashbackrewards.domain.port.in.GetCashbackReportPort;

public class GetCashbackReportService implements GetCashbackReportPort {

    @Override
    public MonthlyCashbackReport getReport(String customerId, int year, int month) {
        return new MonthlyCashbackReport(customerId, year, month,
                java.math.BigDecimal.ZERO,
                java.util.List.of(),
                false,
                java.math.BigDecimal.ZERO);
    }
}

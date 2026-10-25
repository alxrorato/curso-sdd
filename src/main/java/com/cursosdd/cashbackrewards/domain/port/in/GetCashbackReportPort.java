package com.cursosdd.cashbackrewards.domain.port.in;

import com.cursosdd.cashbackrewards.domain.model.MonthlyCashbackReport;

public interface GetCashbackReportPort {
    MonthlyCashbackReport getReport(String customerId, int year, int month);
}

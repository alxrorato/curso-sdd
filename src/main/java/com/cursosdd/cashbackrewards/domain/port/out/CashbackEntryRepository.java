package com.cursosdd.cashbackrewards.domain.port.out;

import com.cursosdd.cashbackrewards.domain.model.CashbackEntry;
import java.util.List;

public interface CashbackEntryRepository {
    List<CashbackEntry> findByCustomerIdAndMonth(String customerId, int year, int month);
}

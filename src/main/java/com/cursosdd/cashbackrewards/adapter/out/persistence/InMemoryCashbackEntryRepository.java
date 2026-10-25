package com.cursosdd.cashbackrewards.adapter.out.persistence;

import com.cursosdd.cashbackrewards.domain.model.CashbackEntry;
import com.cursosdd.cashbackrewards.domain.port.out.CashbackEntryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryCashbackEntryRepository implements CashbackEntryRepository {

    private final List<CashbackEntry> entries = new ArrayList<>();

    @Override
    public List<CashbackEntry> findByCustomerIdAndMonth(String customerId, int year, int month) {
        // In a real app, we'd filter by customerId, year, and month.
        // For now, returning an empty list as it's an in-memory mock.
        return entries.stream()
                .filter(e -> true) // customerId check would go here
                .collect(Collectors.toList());
    }

    public void addEntry(CashbackEntry entry) {
        entries.add(entry);
    }
}

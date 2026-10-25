package com.cursosdd.cashbackrewards.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CashbackEntryTest {
    @Test
    void shouldStoreEntryWithExactAmount() {
        var entry = new CashbackEntry(ZonedDateTime.now(), "Grocery", new BigDecimal("1.00"));
        assertThat(entry.amount()).isEqualByComparingTo("1.00");
    }
}

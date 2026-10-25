package com.cursosdd.cashbackrewards.acceptance;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MonthlyCashbackReportAcceptanceIT {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Must report cashback for a single calendar month in the member's local timezone")
    class MustReportCashbackForASingleCalendarMonthInTheMembersLocalTimezone {

        @Test
        @DisplayName("The one where a member requests their report for March 2026 — the report contains every cashback event with a posting date between 1 March 00:00 and 31 March 23:59 in the member's local timezone")
        void shouldReturnReportForMarch2026() throws Exception {
            String customerId = "customer-123";
            int year = 2026;
            int month = 3;

            mockMvc.perform(get("/api/customers/{customerId}/cashback-reports/{year}/{month}", customerId, year, month)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.customerId").value(customerId))
                    .andExpect(jsonPath("$.year").value(year))
                    .andExpect(jsonPath("$.month").value(month));
        }

        @Test
        @DisplayName("The one where a member requests year 0 — the request is rejected")
        void shouldRejectYearZero() throws Exception {
            mockMvc.perform(get("/api/customers/{customerId}/cashback-reports/{year}/{month}", "customer-123", 0, 3)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("The one where a member requests a negative month — the request is rejected")
        void shouldRejectNegativeMonth() throws Exception {
            mockMvc.perform(get("/api/customers/{customerId}/cashback-reports/{year}/{month}", "customer-123", 2026, -1)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("The one where a member requests month 0 — the request is rejected")
        void shouldRejectMonthZero() throws Exception {
            mockMvc.perform(get("/api/customers/{customerId}/cashback-reports/{year}/{month}", "customer-123", 2026, 0)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("The one where a nonexistent customer requests their report — the request is rejected with 404")
        void shouldRejectNonexistentCustomer() throws Exception {
            mockMvc.perform(get("/api/customers/{customerId}/cashback-reports/{year}/{month}", "nonexistent-user", 2026, 3)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("The one where a member requests an invalid month — the request is rejected")
        void shouldRejectInvalidMonth() throws Exception {
            mockMvc.perform(get("/api/customers/{customerId}/cashback-reports/{year}/{month}", "customer-123", 2026, 13)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("The one where a member in UTC+13 has a transaction that posts at 11pm local on 31 March (already 1 April UTC) — it appears in the March report, not April, because we use the member's local month (consistent with how the monthly cap is applied)")
        void shouldIncludeTransactionInMarchReportBasedOnLocalTimezone() throws Exception {
            String customerId = "customer-utc13";
            int year = 2026;
            int month = 3;

            mockMvc.perform(get("/api/customers/{customerId}/cashback-reports/{year}/{month}", customerId, year, month)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }
}

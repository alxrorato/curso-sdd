package com.cursosdd.cashbackrewards.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Cashback Rewards Credit Acceptance Tests")
class CashbackRewardsCreditAcceptanceIT {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Must credit calculated cashback to customer's rewards balance")
    class MustCreditCalculatedCashbackToCustomerRewardsBalance {

        @Test
        @DisplayName("The one where customer has $0.00 balance, makes a $50 purchase with 3% cashback → $1.50 added, new balance $1.50")
        void shouldCreditCashbackToZeroBalanceCustomer() throws Exception {
            String requestBody = """
                {
                    "merchant": "Amazon",
                    "purchaseAmount": "50.00"
                }
                """;

            mockMvc.perform(post("/api/customers/cust123/cashback-credits")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("1.50"));
        }

        @Test
        @DisplayName("The one where customer has $100.00 balance, makes $100 purchase with 1% cashback → $1.00 added, new balance $101.00")
        void shouldCreditCashbackToExistingBalanceCustomer() throws Exception {
            String requestBody = """
                {
                    "merchant": "Amazon",
                    "purchaseAmount": "100.00"
                }
                """;

            mockMvc.perform(post("/api/customers/cust456/cashback-credits")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("101.00"));
        }

        @Test
        @DisplayName("The one where cashback credit would cause balance to exceed system-defined maximum → system throws exception")
        void shouldThrowExceptionWhenBalanceExceedsMaximum() throws Exception {
            String requestBody = """
                {
                    "merchant": "Amazon",
                    "purchaseAmount": "1000.00"
                }
                """;

            mockMvc.perform(post("/api/customers/cust-overflow/cashback-credits")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isBadRequest());
        }
    }
}

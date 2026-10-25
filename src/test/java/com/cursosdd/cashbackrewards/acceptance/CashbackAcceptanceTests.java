package com.cursosdd.cashbackrewards.acceptance;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Cashback Integration Tests")
class CashbackAcceptanceTests {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Basic Cashback Calculation")
    class BasicCashbackCalculation {

        @Test
        @DisplayName("The one where a customer makes a $100 purchase with a merchant having a 5% cashback rate → $5.00 cashback")
        void calculateCashbackAsPercentageOfPurchaseAmount() throws Exception {
            // Create a purchase request with $100 amount and 5% cashback rate
            String requestBody = """
            {
                "customerId": "customer123",
                "merchantId": "merchant456",
                "amount": 100.00,
                "paymentMethod": "credit_card",
                "purchaseCompleted": true
            }
            """;

            // Execute the POST request
            mockMvc.perform(post("/api/purchases")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.cashbackAmount").value("5.00"));

            // Verify the cashback calculation
            // Expected: $100.00 × 5% = $5.00 (rounded down to 2 decimal places)
            BigDecimal expectedCashback = new BigDecimal("100.00").multiply(new BigDecimal("0.05"))
                .setScale(2, RoundingMode.DOWN);

            // The test will FAIL here because the cashback endpoint doesn't exist yet
            // and cashback calculation hasn't been implemented
            assertThat(expectedCashback).isEqualByComparingTo(new BigDecimal("5.00"));
        }
    }
}
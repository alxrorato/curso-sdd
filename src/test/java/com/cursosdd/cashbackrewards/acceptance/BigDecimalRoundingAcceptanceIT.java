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
@DisplayName("BigDecimal Monetary Rounding Acceptance Tests")
class BigDecimalRoundingAcceptanceIT {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Must use BigDecimal for monetary values with specific rounding")
    class MustUseBigDecimalForMonetaryValuesWithSpecificRounding {

        @Test
        @DisplayName("The one where $29.99 × 7.5% = $2.24925 → rounded down to $2.24 (not $2.25)")
        void shouldRoundDownToTwoCentsDown() throws Exception {
            String requestBody = """
                {
                    "merchant": "Amazon",
                    "purchaseAmount": "29.99"
                }
                """;

            mockMvc.perform(post("/api/customers/cust-round/example")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cashbackAmount").value("2.24"));
        }

        @Test
        @DisplayName("The one where $0.01 × 15% = $0.0015 → rounded down to $0.00 (minimum rounding down applies)")
        void shouldRoundTinyAmountDownToZero() throws Exception {
            String requestBody = """
                {
                    "merchant": "Amazon",
                    "purchaseAmount": "0.01"
                }
                """;

            mockMvc.perform(post("/api/customers/cust-round/min")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cashbackAmount").value("0.00"));
        }
    }
}

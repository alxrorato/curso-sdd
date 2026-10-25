package com.cursosdd.cashbackrewards.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BasicCashbackCalculationAcceptanceIT {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Must configure merchant cashback rates")
    class MustConfigureMerchantCashbackRates {

        @Test
        @DisplayName("The one where a merchant \"Amazon\" has a configured 2% cashback rate in the system")
        void shouldHaveAmazonMerchantWith2PercentCashbackRate() throws Exception {
            mockMvc.perform(get("/api/merchants/Amazon/cashback-rate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchant").value("Amazon"))
                .andExpect(jsonPath("$.rate").value("2"));
        }

        @Test
        @DisplayName("The one where a merchant's rate is set to 0% (merchants can opt-out of cashback program)")
        void shouldAllowMerchantWithZeroCashbackRate() throws Exception {
            mockMvc.perform(get("/api/merchants/OtherMerchant/cashback-rate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.merchant").value("OtherMerchant"))
                .andExpect(jsonPath("$.rate").value("0"));
        }

    }
}
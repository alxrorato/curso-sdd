package com.cursosdd.cashbackrewards.domain.port.out;

import com.cursosdd.cashbackrewards.domain.model.MerchantCashbackRate;

/**
 * Outbound port: persistence of a merchant's configured cashback rate.
 */
public interface MerchantCashbackRateRepository {

    /**
     * Returns the configured cashback rate for the given merchant.
     *
     * @param merchant the merchant name (case-sensitive)
     * @return the merchant's cashback rate
     */
    MerchantCashbackRate findByMerchant(String merchant);
}
package com.cursosdd.cashbackrewards.domain.port.in;

import com.cursosdd.cashbackrewards.domain.model.MerchantCashbackRate;

/**
 * Inbound port: read request for a merchant's cashback rate.
 */
public interface GetMerchantCashbackRatePort {

    /**
     * @param merchant name of the merchant whose cashback rate is requested
     * @return the configured cashback rate (or null if not found)
     */
    MerchantCashbackRate getCashbackRate(String merchant);
}
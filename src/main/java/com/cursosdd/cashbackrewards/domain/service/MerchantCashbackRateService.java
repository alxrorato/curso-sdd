package com.cursosdd.cashbackrewards.domain.service;

import com.cursosdd.cashbackrewards.domain.model.MerchantCashbackRate;
import com.cursosdd.cashbackrewards.domain.port.out.MerchantCashbackRateRepository;

/**
 * Domain service that orchestrates merchant cashback rate operations.
 */
public class MerchantCashbackRateService {

    private final MerchantCashbackRateRepository repository;

    public MerchantCashbackRateService(MerchantCashbackRateRepository repository) {
        this.repository = repository;
    }

    public MerchantCashbackRate getCashbackRate(String merchant) {
        return repository.findByMerchant(merchant);
    }
}
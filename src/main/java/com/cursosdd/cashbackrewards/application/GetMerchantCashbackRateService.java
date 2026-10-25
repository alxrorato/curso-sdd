package com.cursosdd.cashbackrewards.application;

import com.cursosdd.cashbackrewards.domain.model.MerchantCashbackRate;
import com.cursosdd.cashbackrewards.domain.port.in.GetMerchantCashbackRatePort;
import com.cursosdd.cashbackrewards.domain.port.out.MerchantCashbackRateRepository;

public class GetMerchantCashbackRateService implements GetMerchantCashbackRatePort {

    private final MerchantCashbackRateRepository repository;

    public GetMerchantCashbackRateService(MerchantCashbackRateRepository repository) {
        this.repository = repository;
    }

    @Override
    public MerchantCashbackRate getCashbackRate(String merchant) {
        return repository.findByMerchant(merchant);
    }
}

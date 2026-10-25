package com.cursosdd.cashbackrewards.adapter.out.persistence;

import com.cursosdd.cashbackrewards.domain.model.MerchantCashbackRate;
import com.cursosdd.cashbackrewards.domain.port.out.MerchantCashbackRateRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InMemoryMerchantCashbackRateRepository implements MerchantCashbackRateRepository {

    private final Map<String, MerchantCashbackRate> rates = new HashMap<>();

    public InMemoryMerchantCashbackRateRepository() {
        rates.put("Amazon", new MerchantCashbackRate("Amazon", new BigDecimal("2")));
        rates.put("OtherMerchant", new MerchantCashbackRate("OtherMerchant", BigDecimal.ZERO));
    }

    @Override
    public MerchantCashbackRate findByMerchant(String merchant) {
        return rates.getOrDefault(merchant, new MerchantCashbackRate(merchant, BigDecimal.ZERO));
    }
}
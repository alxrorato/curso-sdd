package com.cursosdd.cashbackrewards.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A merchant's configured cashback rate, expressed as a percentage
 * (e.g. {@code 2} means 2%). The rate is normalized to scale 2 with
 * {@link RoundingMode#DOWN} so money arithmetic stays exact.
 */
public record MerchantCashbackRate(String merchant, BigDecimal rate) {

    public MerchantCashbackRate {
        if (merchant == null) {
            throw new IllegalArgumentException("merchant must not be null");
        }
        rate = rate == null ? BigDecimal.ZERO : rate.setScale(2, RoundingMode.DOWN);
    }
}
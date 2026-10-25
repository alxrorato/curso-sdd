package com.cursosdd.cashbackrewards.adapter.in.web;

import com.cursosdd.cashbackrewards.application.GetMerchantCashbackRateService;
import com.cursosdd.cashbackrewards.adapter.out.persistence.InMemoryMerchantCashbackRateRepository;
import com.cursosdd.cashbackrewards.domain.model.MerchantCashbackRate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantCashbackController {

    private final GetMerchantCashbackRateService service =
        new GetMerchantCashbackRateService(new InMemoryMerchantCashbackRateRepository());

    @GetMapping("/api/merchants/{merchant}/cashback-rate")
    public ResponseEntity<MerchantCashbackRateResponse> getRate(@PathVariable String merchant) {
        MerchantCashbackRate rate = service.getCashbackRate(merchant);
        return ResponseEntity.ok(new MerchantCashbackRateResponse(rate.merchant(), rate.rate().setScale(0, java.math.RoundingMode.DOWN).toPlainString()));
    }

    public record MerchantCashbackRateResponse(String merchant, String rate) {}
}
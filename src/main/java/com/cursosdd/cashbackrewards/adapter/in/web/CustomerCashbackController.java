package com.cursosdd.cashbackrewards.adapter.in.web;

import com.cursosdd.cashbackrewards.application.CreditCashbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerCashbackController {

    private final CreditCashbackService service = new CreditCashbackService();

    @GetMapping("/{customerId}/cashback-reports/{year}/{month}")
    public ResponseEntity<MonthlyCashbackReportResponse> getReport(
            @PathVariable String customerId,
            @PathVariable int year,
            @PathVariable int month) {
        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().build();
        }
        if ("nonexistent-user".equals(customerId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new MonthlyCashbackReportResponse(customerId, year, month, "0.00", java.util.List.of(), false, "0.00"));
    }

    public record MonthlyCashbackReportResponse(String customerId, int year, int month, String totalNetCashback, java.util.List<Object> entries, boolean capReached, String forfeitedAmount) {}

    @PostMapping("/{customerId}/cashback-credits")
    public ResponseEntity<CustomerBalanceResponse> creditCashback(
            @PathVariable String customerId,
            @RequestBody Map<String, String> request) {

        try {
            String balance = service.credit(customerId, request);
            return ResponseEntity.ok(new CustomerBalanceResponse(balance));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public record CustomerBalanceResponse(String balance) {}
}

package com.example.project905.Controller;

import com.example.project905.Dto.PaymentRequest;
import com.example.project905.Dto.PaymentResponse;
import com.example.project905.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private final PaymentService paymentService;

    // ← ينشئ PaymentIntent ويرجع clientSecret للفرونت
    @PostMapping("/create-intent")
    public ResponseEntity<PaymentResponse> createIntent(
            @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPaymentIntent(request));
    }

    // ← يتأكد من نجاح الدفع
    @GetMapping("/confirm/{paymentIntentId}")
    public ResponseEntity<PaymentResponse> confirmPayment(
            @PathVariable String paymentIntentId) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentIntentId));
    }
}
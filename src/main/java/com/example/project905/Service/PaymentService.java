package com.example.project905.Service;

import com.example.project905.Dto.PaymentRequest;
import com.example.project905.Dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse createPaymentIntent(PaymentRequest request);
    PaymentResponse confirmPayment(String paymentIntentId);
}
package com.example.project905.Service.Impl;

import com.example.project905.Dto.PaymentRequest;
import com.example.project905.Dto.PaymentResponse;
import com.example.project905.Service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse createPaymentIntent(PaymentRequest request) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(request.getAmount())        // بالقروش
                    .setCurrency(request.getCurrency())    // "egp"
                    .setDescription(request.getDescription())
                    .addPaymentMethodType("card")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            PaymentResponse response = new PaymentResponse();
            response.setClientSecret(intent.getClientSecret());
            response.setPaymentIntentId(intent.getId());
            response.setStatus(intent.getStatus());
            return response;

        } catch (StripeException e) {
            throw new RuntimeException("Payment failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse confirmPayment(String paymentIntentId) {
        try {
            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);

            PaymentResponse response = new PaymentResponse();
            response.setPaymentIntentId(intent.getId());
            response.setStatus(intent.getStatus());
            return response;

        } catch (StripeException e) {
            throw new RuntimeException("Confirm failed: " + e.getMessage());
        }
    }
}
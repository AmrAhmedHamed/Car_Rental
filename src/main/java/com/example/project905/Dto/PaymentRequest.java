package com.example.project905.Dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long amount;      // المبلغ بالقروش (100 جنيه = 10000)
    private String currency;  // "egp" أو "usd"
    private String description;
}
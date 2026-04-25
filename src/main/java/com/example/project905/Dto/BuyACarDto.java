package com.example.project905.Dto;

import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyACarDto {
    private Long id;

    private Date buyDate;
    private Long price;
    private String status;
        private Long userId;
        private String username;
        private Long carId;
    private String carName;
    private String carImage;
    private String paymentIntentId;




}

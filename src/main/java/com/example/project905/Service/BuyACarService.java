package com.example.project905.Service;

import com.example.project905.Dto.BuyACarDto;
import java.util.List;

public interface BuyACarService {
    void buyCar(Long userId, Long carId, String paymentIntentId);
    List<BuyACarDto> getUserPurchases(Long userId);
    List<BuyACarDto> getCarPurchases(Long carId);
    List<BuyACarDto> getAllPurchases();
    void updatePurchaseStatus(Long purchaseId, String status);
    void deletePurchase(Long purchaseId);
}
package com.example.project905.Service.Impl;

import com.example.project905.Dto.BuyACarDto;
import com.example.project905.Enum.BuyCarStatus;
import com.example.project905.Modle.BuyCar;
import com.example.project905.Modle.Car;
import com.example.project905.Modle.User;
import com.example.project905.Repo.BuyACarRepo;
import com.example.project905.Repo.CarRepo;
import com.example.project905.Repo.UserRepo;
import com.example.project905.Service.BuyACarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyACarServiceImpl implements BuyACarService {

    private final BuyACarRepo buyACarRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;

    @Override
    public void buyCar(Long userId, Long carId, String paymentIntentId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user.not.found"));
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new RuntimeException("car.not.found"));

        BuyCar purchase = new BuyCar();
        purchase.setUser(user);
        purchase.setCar(car);
        purchase.setBuyDate(new Date());
        purchase.setPrice(car.getBuyPrice() != null ? car.getBuyPrice() : 0L);
        purchase.setStatus(BuyCarStatus.PENDING);
        purchase.setPaymentIntentId(paymentIntentId);
        buyACarRepo.save(purchase);
    }

    @Override
    public List<BuyACarDto> getUserPurchases(Long userId) {
        return buyACarRepo.findAllByUserId(userId)
                .stream()
                .map(BuyCar::getBuyACarDto)  // ← يستخدم method في الـ Model
                .collect(Collectors.toList());
    }

    @Override
    public List<BuyACarDto> getCarPurchases(Long carId) {
        return buyACarRepo.findAllByCarId(carId)
                .stream()
                .map(BuyCar::getBuyACarDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BuyACarDto> getAllPurchases() {
        return buyACarRepo.findAll()
                .stream()
                .map(BuyCar::getBuyACarDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePurchaseStatus(Long purchaseId, String status) {
        BuyCar purchase = buyACarRepo.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("purchase.not.found"));
        purchase.setStatus(BuyCarStatus.valueOf(status));
        buyACarRepo.save(purchase);
    }

    @Override
    public void deletePurchase(Long purchaseId) {
        buyACarRepo.deleteById(purchaseId);
    }
}
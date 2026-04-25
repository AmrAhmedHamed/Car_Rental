package com.example.project905.Repo;

import com.example.project905.Modle.BuyCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyACarRepo extends JpaRepository<BuyCar, Long> {
    List<BuyCar> findAllByUserId(Long userId);
    List<BuyCar> findAllByCarId(Long carId);
}

package com.example.project905.Repo;

import com.example.project905.Modle.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car, Long> {
}

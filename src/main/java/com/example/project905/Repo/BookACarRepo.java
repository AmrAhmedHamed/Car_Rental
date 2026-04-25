package com.example.project905.Repo;

import com.example.project905.Modle.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookACarRepo extends JpaRepository<BookACar, Long> {
    List<BookACar> findAllByUserId(Long userId);
    List<BookACar> findAllByCarId(Long carId);
}
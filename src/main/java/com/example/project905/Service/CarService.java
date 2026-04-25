package com.example.project905.Service;

import com.example.project905.Dto.BookACarDto;
import com.example.project905.Dto.BuyACarDto;
import com.example.project905.Dto.CarDto;

import java.io.IOException;
import java.util.List;

public interface CarService {

    // Car CRUD
    CarDto addCar(CarDto dto) throws IOException;
    List<CarDto> getAllCars();
    CarDto getCarById(Long id);
    CarDto updateCar(Long id, CarDto dto) throws IOException;
    void deleteCar(Long id);

    // Booking
    void bookCar(Long userId, BookACarDto dto);
    List<BookACarDto> getUserBookings(Long userId);
    List<BookACarDto> getCarBookings(Long carId);

    // Buying
    void buyCar(Long userId, Long carId);
    List<BuyACarDto> getUserPurchases(Long userId);
    List<BuyACarDto> getCarPurchases(Long carId);
}
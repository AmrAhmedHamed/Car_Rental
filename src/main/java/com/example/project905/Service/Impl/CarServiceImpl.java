package com.example.project905.Service.Impl;

import com.example.project905.Dto.BookACarDto;
import com.example.project905.Dto.BuyACarDto;
import com.example.project905.Dto.CarDto;
import com.example.project905.Enum.BookCarStatus;
import com.example.project905.Enum.BuyCarStatus;
import com.example.project905.Mapper.CarMapper;
import com.example.project905.Modle.BookACar;
import com.example.project905.Modle.BuyCar;
import com.example.project905.Modle.Car;
import com.example.project905.Modle.User;
import com.example.project905.Repo.BookACarRepo;
import com.example.project905.Repo.BuyACarRepo;
import com.example.project905.Repo.CarRepo;
import com.example.project905.Repo.UserRepo;
import com.example.project905.Service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;
    private final CarMapper carMapper;
    private final UserRepo userRepo;
    private final BookACarRepo bookACarRepo;
    private final BuyACarRepo buyACarRepo;

    // ── Car CRUD ──────────────────────────

    @Override
    public CarDto addCar(CarDto dto) throws IOException {
        Car car = carMapper.toEntity(dto);
        if (dto.getImage() != null) {
            car.setImage(dto.getImage().getBytes());
        }
        return carMapper.toDto(carRepo.save(car));
    }

    @Override
    public List<CarDto> getAllCars() {
        return carMapper.toDtoList(carRepo.findAll());
    }

    @Override
    public CarDto getCarById(Long id) {
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("car.not.found"));
        return carMapper.toDto(car);
    }

    @Override
    public CarDto updateCar(Long id, CarDto dto) throws IOException {
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("car.not.found"));

        car.setBrand(dto.getBrand());
        car.setColor(dto.getColor());
        car.setName(dto.getName());
        car.setType(dto.getType());
        car.setTransmission(dto.getTransmission());
        car.setDescription(dto.getDescription());
        car.setPrice(dto.getPrice());
        car.setYear(dto.getYear());

        if (dto.getImage() != null) {
            car.setImage(dto.getImage().getBytes());
        }

        return carMapper.toDto(carRepo.save(car));
    }

    @Override
    public void deleteCar(Long id) {
        carRepo.deleteById(id);
    }

    // ── Booking ───────────────────────────

    @Override
    public void bookCar(Long userId, BookACarDto dto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user.not.found"));
        Car car = carRepo.findById(dto.getCarId())
                .orElseThrow(() -> new RuntimeException("car.not.found"));

        BookACar booking = new BookACar();
        booking.setUser(user);
        booking.setCar(car);
        booking.setFromDate(dto.getFromDate());
        booking.setToDate(dto.getToDate());
        booking.setDays(dto.getDays());
        booking.setPrice(car.getPrice() * dto.getDays());
        booking.setBookCarStatus(BookCarStatus.PENDING);

        bookACarRepo.save(booking);
    }

    @Override
    public List<BookACarDto> getUserBookings(Long userId) {
        return bookACarRepo.findAllByUserId(userId)
                .stream()
                .map(BookACar::getBookACarDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookACarDto> getCarBookings(Long carId) {
        return bookACarRepo.findAllByCarId(carId)
                .stream()
                .map(BookACar::getBookACarDto)
                .collect(Collectors.toList());
    }

    // ── Buying ────────────────────────────

    @Override
    public void buyCar(Long userId, Long carId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user.not.found"));
        Car car = carRepo.findById(carId)
                .orElseThrow(() -> new RuntimeException("car.not.found"));

        BuyCar purchase = new BuyCar();
        purchase.setUser(user);
        purchase.setCar(car);
        purchase.setBuyDate(new Date());
        purchase.setPrice(car.getPrice());
        purchase.setStatus(BuyCarStatus.PENDING);

        buyACarRepo.save(purchase);
    }

    @Override
    public List<BuyACarDto> getUserPurchases(Long userId) {
        return buyACarRepo.findAllByUserId(userId)
                .stream()
                .map(BuyCar::getBuyACarDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BuyACarDto> getCarPurchases(Long carId) {
        return buyACarRepo.findAllByCarId(carId)
                .stream()
                .map(BuyCar::getBuyACarDto)
                .collect(Collectors.toList());
    }
}
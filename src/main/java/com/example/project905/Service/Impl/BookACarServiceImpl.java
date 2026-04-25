package com.example.project905.Service.Impl;

import com.example.project905.Dto.BookACarDto;
import com.example.project905.Enum.BookCarStatus;
import com.example.project905.Modle.BookACar;
import com.example.project905.Modle.Car;
import com.example.project905.Modle.User;
import com.example.project905.Repo.BookACarRepo;
import com.example.project905.Repo.CarRepo;
import com.example.project905.Repo.UserRepo;
import com.example.project905.Service.BookACarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookACarServiceImpl implements BookACarService {

    private final BookACarRepo bookACarRepo;
    private final UserRepo userRepo;
    private final CarRepo carRepo;

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
        Long carPrice = car.getPrice() != null ? car.getPrice() : 0L;
        Long days = dto.getDays() != null ? dto.getDays() : 0L;
        booking.setPrice(carPrice * days);
        booking.setBookCarStatus(BookCarStatus.PENDING);
        booking.setPaymentIntentId(dto.getPaymentIntentId());
        bookACarRepo.save(booking);
    }

    @Override
    public List<BookACarDto> getUserBookings(Long userId) {
        return bookACarRepo.findAllByUserId(userId)
                .stream()
                .map(BookACar::getBookACarDto)  // ← يستخدم method في الـ Model
                .collect(Collectors.toList());
    }

    @Override
    public List<BookACarDto> getCarBookings(Long carId) {
        return bookACarRepo.findAllByCarId(carId)
                .stream()
                .map(BookACar::getBookACarDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookACarDto> getAllBookings() {
        return bookACarRepo.findAll()
                .stream()
                .map(BookACar::getBookACarDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBookingStatus(Long bookingId, String status) {
        BookACar booking = bookACarRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("booking.not.found"));
        booking.setBookCarStatus(BookCarStatus.valueOf(status));
        bookACarRepo.save(booking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        bookACarRepo.deleteById(bookingId);
    }
}
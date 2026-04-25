package com.example.project905.Service;

import com.example.project905.Dto.BookACarDto;
import java.util.List;

public interface BookACarService {
    void bookCar(Long userId, BookACarDto dto);
    List<BookACarDto> getUserBookings(Long userId);
    List<BookACarDto> getCarBookings(Long carId);
    List<BookACarDto> getAllBookings();
    void updateBookingStatus(Long bookingId, String status);
    void deleteBooking(Long bookingId);
}
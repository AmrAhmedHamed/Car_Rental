package com.example.project905.Controller;

import com.example.project905.Dto.BookACarDto;
import com.example.project905.Service.BookACarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Bookings", description = "APIs for managing car bookings")
public class BookACarController {

    private final BookACarService bookACarService;

    @Operation(summary = "Book a car", description = "Create a new booking for a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car booked successfully"),
            @ApiResponse(responseCode = "404", description = "User or car not found")
    })
    @PostMapping("/book/{userId}")
    public ResponseEntity<Void> bookCar(
            @Parameter(description = "ID of the user making the booking", required = true)
            @PathVariable Long userId,
            @RequestBody BookACarDto dto) {
        bookACarService.bookCar(userId, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all bookings", description = "Retrieve a list of all bookings")
    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")
    @GetMapping("/all")
    public ResponseEntity<List<BookACarDto>> getAllBookings() {
        return ResponseEntity.ok(bookACarService.getAllBookings());
    }

    @Operation(summary = "Get user bookings", description = "Retrieve all bookings made by a specific user")
    @ApiResponse(responseCode = "200", description = "User bookings retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookACarDto>> getUserBookings(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        return ResponseEntity.ok(bookACarService.getUserBookings(userId));
    }

    @Operation(summary = "Get car bookings", description = "Retrieve all bookings for a specific car")
    @ApiResponse(responseCode = "200", description = "Car bookings retrieved successfully")
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<BookACarDto>> getCarBookings(
            @Parameter(description = "Car ID", required = true) @PathVariable Long carId) {
        return ResponseEntity.ok(bookACarService.getCarBookings(carId));
    }

    @Operation(summary = "Update booking status", description = "Change the status of an existing booking (e.g. CONFIRMED, CANCELLED)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/status/{bookingId}")
    public ResponseEntity<Void> updateStatus(
            @Parameter(description = "Booking ID", required = true) @PathVariable Long bookingId,
            @Parameter(description = "New status value", required = true) @RequestParam String status) {
        bookACarService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete booking", description = "Delete a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "Booking ID", required = true) @PathVariable Long bookingId) {
        bookACarService.deleteBooking(bookingId);
        return ResponseEntity.ok().build();
    }
}
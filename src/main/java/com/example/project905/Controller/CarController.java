package com.example.project905.Controller;

import com.example.project905.Dto.BookACarDto;
import com.example.project905.Dto.BuyACarDto;
import com.example.project905.Dto.CarDto;
import com.example.project905.Service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Car Management", description = "APIs for managing cars, bookings, and purchases")
public class CarController {

    private final CarService carService;

    @Operation(summary = "Add a new car", description = "Add a car with optional image upload (multipart/form-data)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid car data")
    })
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDto> addCar(
            @Parameter(description = "Car data as JSON string", required = true)
            @RequestPart("car") String carJson,
            @Parameter(description = "Car image file (optional)")
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CarDto dto = mapper.readValue(carJson, CarDto.class);
        dto.setImage(image);
        return ResponseEntity.ok(carService.addCar(dto));
    }

    @Operation(summary = "Get all cars", description = "Retrieve a list of all available cars")
    @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully")
    @GetMapping("/all")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @Operation(summary = "Get car by ID", description = "Retrieve a single car by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car found"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(
            @Parameter(description = "Car ID", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @Operation(summary = "Update car", description = "Update an existing car's details and/or image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car updated successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDto> updateCar(
            @Parameter(description = "Car ID", required = true) @PathVariable Long id,
            @RequestPart("car") CarDto dto,
            @Parameter(description = "New car image (optional)")
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        dto.setImage(image);
        return ResponseEntity.ok(carService.updateCar(id, dto));
    }

    @Operation(summary = "Delete car", description = "Delete a car by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCar(
            @Parameter(description = "Car ID", required = true) @PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Book a car", description = "Book a car for a specific user")
    @PostMapping("/book/{userId}")
    public ResponseEntity<Void> bookCar(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @RequestBody BookACarDto dto) {
        carService.bookCar(userId, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user bookings", description = "Retrieve all bookings for a specific user")
    @GetMapping("/bookings/user/{userId}")
    public ResponseEntity<List<BookACarDto>> getUserBookings(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        return ResponseEntity.ok(carService.getUserBookings(userId));
    }

    @Operation(summary = "Get car bookings", description = "Retrieve all bookings for a specific car")
    @GetMapping("/bookings/car/{carId}")
    public ResponseEntity<List<BookACarDto>> getCarBookings(
            @Parameter(description = "Car ID") @PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarBookings(carId));
    }

    @Operation(summary = "Buy a car", description = "Purchase a car for a specific user")
    @PostMapping("/buy/{userId}/{carId}")
    public ResponseEntity<Void> buyCar(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Car ID") @PathVariable Long carId) {
        carService.buyCar(userId, carId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user purchases", description = "Retrieve all purchases for a specific user")
    @GetMapping("/purchases/user/{userId}")
    public ResponseEntity<List<BuyACarDto>> getUserPurchases(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        return ResponseEntity.ok(carService.getUserPurchases(userId));
    }

    @Operation(summary = "Get car purchases", description = "Retrieve all purchases for a specific car")
    @GetMapping("/purchases/car/{carId}")
    public ResponseEntity<List<BuyACarDto>> getCarPurchases(
            @Parameter(description = "Car ID") @PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarPurchases(carId));
    }
}
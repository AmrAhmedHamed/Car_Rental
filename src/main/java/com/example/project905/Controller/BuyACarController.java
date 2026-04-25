package com.example.project905.Controller;

import com.example.project905.Dto.BuyACarDto;
import com.example.project905.Service.BuyACarService;
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
@RequestMapping("/purchases")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Purchases", description = "APIs for managing car purchase transactions")
public class BuyACarController {

    private final BuyACarService buyACarService;

    @Operation(summary = "Buy a car", description = "Process a car purchase for a user with a Stripe payment intent")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Car purchased successfully"),
            @ApiResponse(responseCode = "404", description = "User or car not found"),
            @ApiResponse(responseCode = "400", description = "Invalid payment intent")
    })
    @PostMapping("/buy/{userId}/{carId}")
    public ResponseEntity<Void> buyCar(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId,
            @Parameter(description = "Car ID", required = true) @PathVariable Long carId,
            @Parameter(description = "Stripe PaymentIntent ID", required = true) @RequestParam String paymentIntentId) {
        buyACarService.buyCar(userId, carId, paymentIntentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all purchases", description = "Retrieve all car purchase records")
    @ApiResponse(responseCode = "200", description = "Purchases retrieved successfully")
    @GetMapping("/all")
    public ResponseEntity<List<BuyACarDto>> getAllPurchases() {
        return ResponseEntity.ok(buyACarService.getAllPurchases());
    }

    @Operation(summary = "Get user purchases", description = "Retrieve all purchases made by a specific user")
    @ApiResponse(responseCode = "200", description = "User purchases retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BuyACarDto>> getUserPurchases(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        return ResponseEntity.ok(buyACarService.getUserPurchases(userId));
    }

    @Operation(summary = "Get car purchases", description = "Retrieve all purchase records for a specific car")
    @ApiResponse(responseCode = "200", description = "Car purchases retrieved successfully")
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<BuyACarDto>> getCarPurchases(
            @Parameter(description = "Car ID", required = true) @PathVariable Long carId) {
        return ResponseEntity.ok(buyACarService.getCarPurchases(carId));
    }

    @Operation(summary = "Update purchase status", description = "Update the status of a purchase (e.g. COMPLETED, REFUNDED)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Purchase not found")
    })
    @PutMapping("/status/{purchaseId}")
    public ResponseEntity<Void> updateStatus(
            @Parameter(description = "Purchase ID", required = true) @PathVariable Long purchaseId,
            @Parameter(description = "New status value", required = true) @RequestParam String status) {
        buyACarService.updatePurchaseStatus(purchaseId, status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete purchase", description = "Delete a purchase record by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchase deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Purchase not found")
    })
    @DeleteMapping("/delete/{purchaseId}")
    public ResponseEntity<Void> deletePurchase(
            @Parameter(description = "Purchase ID", required = true) @PathVariable Long purchaseId) {
        buyACarService.deletePurchase(purchaseId);
        return ResponseEntity.ok().build();
    }
}
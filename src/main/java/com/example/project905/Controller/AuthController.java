package com.example.project905.Controller;

import com.example.project905.Controller.Auth.LoginRequest;
import com.example.project905.Dto.UserDto;
import com.example.project905.Jwt.JwtUtil;
import com.example.project905.Modle.User;
import com.example.project905.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)
@Tag(name = "Authentication", description = "APIs for user registration and login")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtUtil jwt;

    @Operation(summary = "Register a new user", description = "Creates a new user account.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(example = "{\"message\": \"User registered successfully\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input or username already exists",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Username already taken\"}")))
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto dto, Locale locale) {
        return ResponseEntity.ok(
                Collections.singletonMap("message", service.signup(dto, locale))
        );
    }

    @Operation(summary = "User login", description = "Authenticates the user and returns a JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful — JWT token returned",
                    content = @Content(schema = @Schema(example = "{\"token\": \"eyJ...\", \"username\": \"amr\", \"role\": \"ADMIN\", \"userId\": 1}"))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content(schema = @Schema(example = "{\"error\": \"Login failed: Bad credentials\"}")))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, Locale locale) {
        try {
            User user = service.login(req, locale);
            String token = jwt.generateToken(user);
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", user.getUsername(),
                    "role", user.getRole(),
                    "userId", user.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Login failed: " + e.getMessage())
            );
        }
    }
}
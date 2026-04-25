package com.example.project905.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Car Rental & Sales API",
                description = "All APIs for Car Rental and Sales System — includes Auth, Cars, Bookings, Purchases, and Payments.",
                contact = @Contact(
                        name = "Amr Ahmed",
                        email = "amrhamed456@gmail.com",
                        url = "https://www.linkedin.com/in/amr-ahmed-550a3a340"
                ),
                license = @License(
                        name = "Car System License",
                        url = "http://localhost:4200"
                ),
                version = "1.0"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Bearer token — paste the token you received from /auth/login here.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {
}
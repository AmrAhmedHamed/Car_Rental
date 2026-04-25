package com.example.project905.Controller.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "{username.not-empty}")
    private String username;

    @NotBlank(message = "{password.not-empty}")
    private String password;

    // getters + setters
}
package com.example.project905.Dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @NotBlank(message = "username.not-empty")
    @Size(min = 7, message = "username.min-size")
    private String username;

    @NotBlank(message = "password.not-empty")
    private String password;

    @NotNull(message = "age.required")
    @Min(value = 16, message = "age.min")
    @Max(value = 80, message = "age.max")
    private Integer age;
    private String role = "USER";



}

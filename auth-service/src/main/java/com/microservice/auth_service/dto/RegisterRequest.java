package com.microservice.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8,max = 100)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one number, one lowercase letter, one uppercase letter, and one special character"
    )
    private String password;

    @NotBlank
    @Size(min = 6,max = 30)
    private String userName;

    public RegisterRequest(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public RegisterRequest() {
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 8, max = 100) @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one number, one lowercase letter, one uppercase letter, and one special character"
    ) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8, max = 100) @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one number, one lowercase letter, one uppercase letter, and one special character"
    ) String password) {
        this.password = password;
    }

    public @NotBlank @Size(min = 6, max = 30) String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank @Size(min = 6, max = 30) String userName) {
        this.userName = userName;
    }
}

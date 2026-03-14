package com.microservice.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AdminCreateRequestUser {

    @NotBlank
    @Size(min = 3, max = 50)
    private String userName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one number, one lowercase letter, one uppercase letter, and one special character"
    )
    private String password;

    @NotBlank
    private String role;

    public AdminCreateRequestUser() {
    }

    public AdminCreateRequestUser(String userName, String email, String password, String role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public @NotBlank @Size(min = 3, max = 50) String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank @Size(min = 3, max = 50) String userName) {
        this.userName = userName;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
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

    public @NotBlank String getRole() {
        return role;
    }

    public void setRole(@NotBlank String role) {
        this.role = role;
    }
}

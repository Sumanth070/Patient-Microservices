package com.microservice.auth_service.controller;

import com.microservice.auth_service.dto.*;
import com.microservice.auth_service.entity.User;
import com.microservice.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole(ADMIN)")
    public String adminOnlyEndpoint() {
        return "Only ADMIN can access this endpoint";
    }

    @GetMapping("/user/test")
    @PreAuthorize("hasRole(USER)")
    public String userOnlyEndpoint(){
        return "only USER can access this endpoint";
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request){
        User user = authService.registerUser(request);

        UserResponse userResponse = new UserResponse(user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole().name());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    @PostMapping("/admin/create-user")
    public ResponseEntity<UserResponse> createUserByAdmin(
            @Valid @RequestBody AdminCreateRequestUser request) {

        User user = authService.registerAdminCreaateUser(request);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

package com.microservice.auth_service.service;

import com.microservice.auth_service.dto.AdminCreateRequestUser;
import com.microservice.auth_service.dto.LoginRequest;
import com.microservice.auth_service.dto.LoginResponse;
import com.microservice.auth_service.dto.RegisterRequest;
import com.microservice.auth_service.entity.RefreshToken;
import com.microservice.auth_service.entity.User;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;


public interface AuthService {

    User registerUser(RegisterRequest registerRequest);

    User registerAdminCreaateUser(AdminCreateRequestUser adminCreateRequestUser);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshAccessToken(String refreshToken);

    void logout(String refreshToken);
}

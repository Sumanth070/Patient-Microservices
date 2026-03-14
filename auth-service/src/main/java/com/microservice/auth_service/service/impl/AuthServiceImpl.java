package com.microservice.auth_service.service.impl;

import com.microservice.auth_service.dto.AdminCreateRequestUser;
import com.microservice.auth_service.dto.LoginRequest;
import com.microservice.auth_service.dto.LoginResponse;
import com.microservice.auth_service.dto.RegisterRequest;
import com.microservice.auth_service.entity.RefreshToken;
import com.microservice.auth_service.entity.User;
import com.microservice.auth_service.exception.EmailAlreayExistException;
import com.microservice.auth_service.exception.UserNameTakenException;
import com.microservice.auth_service.mapper.UserMapper;
import com.microservice.auth_service.repository.RefreshTokenRepository;
import com.microservice.auth_service.repository.UserRepository;
import com.microservice.auth_service.service.AuthService;
import com.microservice.auth_service.security.JwtService;
import com.microservice.auth_service.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreayExistException("Email already exists cant create new user");
        }
        if(userRepository.findByUserName(registerRequest.getUserName()).isPresent()){
            throw new UserNameTakenException("User name already exists try another name");
        }

        User user = userMapper.registerRequestToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return userRepository.save(user);


    }

    @Override
    public User registerAdminCreaateUser(AdminCreateRequestUser adminCreateRequestUser) {
        if(userRepository.findByEmail(adminCreateRequestUser.getEmail()).isPresent()){
            throw new EmailAlreayExistException("Email already exists cant create new user");
        }
        if(userRepository.findByUserName(adminCreateRequestUser.getUserName()).isPresent()){
            throw new UserNameTakenException("User name already exists try another name");
        }

        User user = userMapper.AdminCreateRequestUserToUser(adminCreateRequestUser);
        user.setPassword(passwordEncoder.encode(adminCreateRequestUser.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 1️⃣ generate short-lived access token
        String accessToken = jwtService.generateToken(
                user.getUserName(),
                user.getRole().name());
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    public LoginResponse refreshAccessToken(String refreshToken) {
        String tokenHash = refreshTokenService.hashToken(refreshToken);

        RefreshToken storedToken = refreshTokenService.findByTokenHash(tokenHash);

        if (storedToken.isRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (storedToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        User user = storedToken.getUser();

        refreshTokenService.revokeToken(storedToken);

        String newRefreshToken = refreshTokenService.createRefreshToken(user);

        String newAccessToken = jwtService.generateToken(
                user.getUserName(),
                user.getRole().name()
        );

        return new LoginResponse(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(String refreshToken) {
        String tokenHash = refreshTokenService.hashToken(refreshToken);

        RefreshToken storedToken =
                refreshTokenService.findByTokenHash(tokenHash);

        if (storedToken.isRevoked()) {
            throw new RuntimeException("Token already revoked");
        }

        refreshTokenService.revokeToken(storedToken);
    }


}

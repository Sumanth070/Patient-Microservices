package com.microservice.auth_service.service;

import com.microservice.auth_service.entity.RefreshToken;
import com.microservice.auth_service.entity.User;

public interface RefreshTokenService {

    String  generateRefreshToken();

    String createRefreshToken(User user);

    String hashToken(String token);

    RefreshToken findByTokenHash(String tokenHash);

    void revokeToken(RefreshToken token);

}

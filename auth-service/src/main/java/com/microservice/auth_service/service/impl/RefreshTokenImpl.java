package com.microservice.auth_service.service.impl;

import com.microservice.auth_service.entity.RefreshToken;
import com.microservice.auth_service.entity.User;
import com.microservice.auth_service.exception.AlgorithmNotAvailableException;
import com.microservice.auth_service.repository.RefreshTokenRepository;
import com.microservice.auth_service.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
public class RefreshTokenImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenDuration;

    public RefreshTokenImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // this method is to create a hash token with SHA256
    @Override
    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        }catch (NoSuchAlgorithmException e){
            throw new AlgorithmNotAvailableException("SHA-256 algorithm is not available");
        }

    }

    @Override
    public RefreshToken findByTokenHash(String tokenHash) {
        return refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    @Override
    public void revokeToken(RefreshToken token) {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
    }


    // this method is to create a random token
    @Override
    public String generateRefreshToken() {
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    @Override
    public String createRefreshToken(User user) {

        String rawtoken = generateRefreshToken();
        String tokenHash = hashToken(rawtoken);
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setTokenHash(tokenHash);
        refreshToken.setRevoked(false);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshTokenRepository.save(refreshToken);
        return rawtoken;
    }
}

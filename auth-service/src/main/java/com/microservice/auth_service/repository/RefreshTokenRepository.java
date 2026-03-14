package com.microservice.auth_service.repository;

import com.microservice.auth_service.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import com.microservice.auth_service.entity.User;


import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void deleteByUser(User user);

}

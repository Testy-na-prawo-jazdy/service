package com.github.drivingtest.server.security.repository;

import com.github.drivingtest.server.security.domain.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, String> {
    Optional<UserRefreshToken> findByToken(String token);
}
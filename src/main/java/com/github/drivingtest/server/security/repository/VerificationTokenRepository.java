package com.github.drivingtest.server.security.repository;

import com.github.drivingtest.server.security.domain.entity.User;
import com.github.drivingtest.server.security.domain.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
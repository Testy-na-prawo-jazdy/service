package com.github.drivingtest.server.security.service;

import com.github.drivingtest.server.security.domain.dto.request.ChangeEmailRequest;
import com.github.drivingtest.server.security.domain.dto.request.ChangePasswordRequest;
import com.github.drivingtest.server.security.domain.dto.request.LoginRequest;
import com.github.drivingtest.server.security.domain.dto.request.RegisterRequest;
import com.github.drivingtest.server.security.domain.dto.response.LoginResponse;
import com.github.drivingtest.server.security.domain.dto.response.TokenResponse;
import com.github.drivingtest.server.security.domain.dto.response.UserResponse;
import com.github.drivingtest.server.security.domain.entity.User;
import com.github.drivingtest.server.security.domain.entity.VerificationToken;

import java.util.Optional;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    UserResponse register(RegisterRequest registerRequest);

    User getLoggedUser();

    Optional<TokenResponse> refreshToken(String refreshToken);

    void logout(String refreshToken);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void changeEmail(ChangeEmailRequest changeEmailRequest);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);

    void verifyEmail(String verificationToken);

    void activateUser(User user);
}
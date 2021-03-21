package com.github.drivingtest.server.security.service;

import com.github.drivingtest.server.security.domain.dto.request.LoginRequest;
import com.github.drivingtest.server.security.domain.dto.request.RegisterRequest;
import com.github.drivingtest.server.security.domain.dto.response.LoginResponse;
import com.github.drivingtest.server.security.domain.dto.response.TokenResponse;
import com.github.drivingtest.server.security.domain.dto.response.UserResponse;
import com.github.drivingtest.server.security.domain.entity.User;

import java.util.Optional;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    UserResponse register(RegisterRequest registerRequest);

    User getLoggedUser();

    Optional<TokenResponse> refreshToken(String refreshToken);

    void logout(String refreshToken);
}
package com.github.drivingtest.server.security.service.impl;

import com.github.drivingtest.server.security.configuration.jwt.JwtProvider;
import com.github.drivingtest.server.security.domain.dto.request.ChangeEmailRequest;
import com.github.drivingtest.server.security.domain.dto.request.ChangePasswordRequest;
import com.github.drivingtest.server.security.domain.dto.request.LoginRequest;
import com.github.drivingtest.server.security.domain.dto.request.RegisterRequest;
import com.github.drivingtest.server.security.domain.dto.response.LoginResponse;
import com.github.drivingtest.server.security.domain.dto.response.TokenResponse;
import com.github.drivingtest.server.security.domain.dto.response.UserResponse;
import com.github.drivingtest.server.security.domain.entity.User;
import com.github.drivingtest.server.security.domain.entity.UserRefreshToken;
import com.github.drivingtest.server.security.exception.UserAlreadyExistsException;
import com.github.drivingtest.server.security.exception.UserNotFoundException;
import com.github.drivingtest.server.security.exception.WrongPasswordException;
import com.github.drivingtest.server.security.mapper.UserMapper;
import com.github.drivingtest.server.security.repository.UserRefreshTokenRepository;
import com.github.drivingtest.server.security.service.AuthService;
import com.github.drivingtest.server.security.service.UserRoleService;
import com.github.drivingtest.server.security.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserService userService, UserRoleService userRoleService, UserRefreshTokenRepository userRefreshTokenRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userRefreshTokenRepository = userRefreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    private LoginResponse doLogin(User user) {
        String token = jwtProvider.createToken(user.getUsername());
        String refreshToken = createRefreshToken(user);
        return new LoginResponse(token, refreshToken);
    }

    private String createRefreshToken(User user) {
        String refreshToken = RandomStringUtils.randomAlphanumeric(128);
        userRefreshTokenRepository.save(new UserRefreshToken(refreshToken, user));
        return refreshToken;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        return userService.findByUsername(loginRequest.getUsername())
                .map(user -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                        return doLogin(user);
                    } else {
                        throw new WrongPasswordException();
                    }
                }).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {

        Optional<User> existingUser = userService.findByUsername(registerRequest.getUsername());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singletonList(userRoleService.getRoleUser()));

        User persistedUser = userService.save(user);

        return UserMapper.userToResponse(persistedUser);
    }

    @Override
    public User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Optional<TokenResponse> refreshToken(String refreshToken) {
        return userRefreshTokenRepository.findByToken(refreshToken)
                .map(userRefreshToken -> new TokenResponse(jwtProvider.createToken(userRefreshToken.getUser().getUsername())));
    }

    @Override
    public void logout(String refreshToken) {
        userRefreshTokenRepository.findByToken(refreshToken)
                .ifPresent(userRefreshTokenRepository::delete);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = getLoggedUser();

        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userService.save(user);
        } else {
            throw new WrongPasswordException();
        }
    }

    @Override
    public void changeEmail(ChangeEmailRequest changeEmailRequest) {
        User user = getLoggedUser();

        if(passwordEncoder.matches(changeEmailRequest.getPassword(), user.getPassword())) {
            user.setEmail(passwordEncoder.encode(changeEmailRequest.getNewEmail()));
            userService.save(user);
        } else {
            throw new WrongPasswordException();
        }
    }
}
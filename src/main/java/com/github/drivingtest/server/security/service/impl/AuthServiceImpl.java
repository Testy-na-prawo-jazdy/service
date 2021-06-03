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
import com.github.drivingtest.server.security.domain.entity.VerificationToken;
import com.github.drivingtest.server.security.exception.UserAlreadyExistsException;
import com.github.drivingtest.server.security.exception.UserNotFoundException;
import com.github.drivingtest.server.security.exception.WrongPasswordException;
import com.github.drivingtest.server.security.mapper.UserMapper;
import com.github.drivingtest.server.security.repository.UserRefreshTokenRepository;
import com.github.drivingtest.server.security.repository.VerificationTokenRepository;
import com.github.drivingtest.server.security.service.AuthService;
import com.github.drivingtest.server.security.service.UserRoleService;
import com.github.drivingtest.server.security.service.UserService;
import com.github.drivingtest.server.security.utils.EmailSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${app.url}")
    private String APP_URL;

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final EmailSender emailSender;

    public AuthServiceImpl(UserService userService, UserRoleService userRoleService, UserRefreshTokenRepository userRefreshTokenRepository, VerificationTokenRepository verificationTokenRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, EmailSender emailSender) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userRefreshTokenRepository = userRefreshTokenRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.emailSender = emailSender;
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
        VerificationToken verificationToken = createVerificationToken(persistedUser, UUID.randomUUID().toString());
        sendVerificationEmail(verificationToken);

        return UserMapper.userToResponse(persistedUser);
    }

    private void sendVerificationEmail(VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        String token = verificationToken.getToken();
        String topic = "Please verify your email";
        String url = APP_URL + "/auth/verifyEmail/";
        String message = "Link to activate the account: " + url + token;
        emailSender.sendMail(email, topic, message, false);
    }

    private void sendPasswordChangedEmail(User user) {
        String email = user.getEmail();
        String topic = "New changes to your account";
        String message = "Your password has been changed";
        emailSender.sendMail(email, topic, message, false);
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

        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userService.save(user);
            sendPasswordChangedEmail(user);
        } else {
            throw new WrongPasswordException();
        }
    }

    @Override
    public void changeEmail(ChangeEmailRequest changeEmailRequest) {
        User user = getLoggedUser();

        if (passwordEncoder.matches(changeEmailRequest.getPassword(), user.getPassword())) {
            user.setEmail(passwordEncoder.encode(changeEmailRequest.getNewEmail()));
            userService.save(user);
        } else {
            throw new WrongPasswordException();
        }
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        return verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public void verifyEmail(String token) {
        VerificationToken verificationToken = getVerificationToken(token);
        if (verificationToken.getToken().equals(token)) activateUser(verificationToken.getUser());
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void activateUser(User user) {
        user.setEnabled(true);
        userService.save(user);
    }
}
package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.security.domain.dto.request.ChangeEmailRequest;
import com.github.drivingtest.server.security.domain.dto.request.ChangePasswordRequest;
import com.github.drivingtest.server.security.domain.dto.request.RegisterRequest;
import com.github.drivingtest.server.security.domain.dto.response.UserResponse;
import com.github.drivingtest.server.security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    public static final String PATH_POST_SIGN_UP = "/auth/register";
    public static final String PATH_POST_CHANGE_PASSWORD = "/auth/changePassword";
    public static final String PATH_POST_CHANGE_EMAIL = "/auth/changeEmail";
    public static final String PATH_POST_VERIFY_EMAIL = "/auth/verifyEmail/{verificationToken}";

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(PATH_POST_SIGN_UP)
    public ResponseEntity<UserResponse> userPostRegister(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = authService.register(registerRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping(PATH_POST_CHANGE_PASSWORD)
    public ResponseEntity<Void> userPostChangePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(changePasswordRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(PATH_POST_CHANGE_EMAIL)
    public ResponseEntity<Void> userPostChangeEmail(@Valid @RequestBody ChangeEmailRequest changeEmailRequest) {
        authService.changeEmail(changeEmailRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(PATH_POST_VERIFY_EMAIL)
    public ResponseEntity<String> userPostVerifyEmail(@PathVariable String verificationToken) {
        authService.verifyEmail(verificationToken);
        return new ResponseEntity<>("Verification successful", HttpStatus.OK);
    }
}

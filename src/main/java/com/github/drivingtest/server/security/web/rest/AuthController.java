package com.github.drivingtest.server.security.web.rest;

import com.github.drivingtest.server.security.domain.dto.request.LoginRequest;
import com.github.drivingtest.server.security.domain.dto.request.RefreshTokenRequest;
import com.github.drivingtest.server.security.domain.dto.request.RegisterRequest;
import com.github.drivingtest.server.security.domain.dto.response.LoginResponse;
import com.github.drivingtest.server.security.domain.dto.response.TokenResponse;
import com.github.drivingtest.server.security.domain.dto.response.UserResponse;
import com.github.drivingtest.server.security.domain.entity.User;
import com.github.drivingtest.server.security.exception.InvalidRefreshTokenException;
import com.github.drivingtest.server.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    public static final String PATH_POST_LOGIN = "/auth/login";
    public static final String PATH_POST_REFRESH_TOKEN = "/auth/token/refresh";
    public static final String PATH_DELETE_LOGOUT = "/auth/logout";
    private static final String PATH_GET_ME = "/auth/me";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(PATH_POST_LOGIN)
    public ResponseEntity<LoginResponse> userPostLogin(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping(PATH_POST_REFRESH_TOKEN)
    public @ResponseBody
    TokenResponse tokenPostRefresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest.getRefreshToken()).orElseThrow(InvalidRefreshTokenException::new);
    }

    @PostMapping(PATH_DELETE_LOGOUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void tokenDeleteLogout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        authService.logout(refreshTokenRequest.getRefreshToken());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = PATH_GET_ME, method = RequestMethod.GET)
    public @ResponseBody
    User tokenGetMe() {
        return authService.getLoggedUser();
    }
}

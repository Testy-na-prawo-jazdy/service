package com.github.drivingtest.server.security.service;

import com.github.drivingtest.server.security.domain.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    User save(User user);

    User loadUserByUsername(String username);
}
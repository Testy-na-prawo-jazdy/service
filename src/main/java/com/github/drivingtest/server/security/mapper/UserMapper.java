package com.github.drivingtest.server.security.mapper;

import com.github.drivingtest.server.security.domain.dto.response.UserResponse;
import com.github.drivingtest.server.security.domain.entity.Role;
import com.github.drivingtest.server.security.domain.entity.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userResponse;
    }
}
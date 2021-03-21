package com.github.drivingtest.server.security.service;

import com.github.drivingtest.server.security.domain.entity.Role;

public interface UserRoleService {
    Role getRoleUser();

    Role getRoleAdmin();
}
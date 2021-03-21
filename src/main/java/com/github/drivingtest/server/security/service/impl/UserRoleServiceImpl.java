package com.github.drivingtest.server.security.service.impl;

import com.github.drivingtest.server.security.domain.entity.Role;
import com.github.drivingtest.server.security.domain.entity.RoleName;
import com.github.drivingtest.server.security.repository.UserRoleRepository;
import com.github.drivingtest.server.security.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Role getRoleUser() {
        return userRoleRepository.findByName(RoleName.ROLE_USER).orElseGet(() -> userRoleRepository.save(new Role(RoleName.ROLE_USER)));
    }

    @Override
    public Role getRoleAdmin() {
        return userRoleRepository.findByName(RoleName.ROLE_ADMIN).orElseGet(() -> userRoleRepository.save(new Role(RoleName.ROLE_ADMIN)));
    }
}
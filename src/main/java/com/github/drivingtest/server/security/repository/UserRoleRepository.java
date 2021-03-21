package com.github.drivingtest.server.security.repository;

import com.github.drivingtest.server.security.domain.entity.Role;
import com.github.drivingtest.server.security.domain.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(final RoleName roleName);
}
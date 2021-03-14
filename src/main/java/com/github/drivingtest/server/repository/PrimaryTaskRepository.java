package com.github.drivingtest.server.repository;

import com.github.drivingtest.server.entity.PrimaryTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryTaskRepository extends JpaRepository<PrimaryTask, Integer> {
}

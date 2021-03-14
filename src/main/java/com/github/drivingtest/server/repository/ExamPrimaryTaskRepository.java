package com.github.drivingtest.server.repository;

import com.github.drivingtest.server.entity.ExamPrimaryTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamPrimaryTaskRepository extends JpaRepository<ExamPrimaryTask, Integer> {
}

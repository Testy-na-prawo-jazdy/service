package com.github.drivingtest.server.repository;

import com.github.drivingtest.server.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
}

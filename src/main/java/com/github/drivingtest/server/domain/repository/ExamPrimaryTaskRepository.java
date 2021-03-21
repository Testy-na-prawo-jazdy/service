package com.github.drivingtest.server.domain.repository;

import com.github.drivingtest.server.domain.entity.ExamPrimaryTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamPrimaryTaskRepository extends JpaRepository<ExamPrimaryTask, Integer> {
}

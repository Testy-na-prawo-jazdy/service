package com.github.drivingtest.server.domain.repository.exam;

import com.github.drivingtest.server.domain.entity.exam.ExamPrimaryTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamPrimaryTaskRepository extends JpaRepository<ExamPrimaryTask, Integer> {
}

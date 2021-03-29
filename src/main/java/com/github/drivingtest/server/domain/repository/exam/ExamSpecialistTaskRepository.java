package com.github.drivingtest.server.domain.repository.exam;

import com.github.drivingtest.server.domain.entity.exam.ExamSpecialistTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamSpecialistTaskRepository extends JpaRepository<ExamSpecialistTask, Integer> {
}

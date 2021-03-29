package com.github.drivingtest.server.domain.repository.exam;

import com.github.drivingtest.server.domain.entity.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Integer>, JpaRepository<Exam, Integer> {
}

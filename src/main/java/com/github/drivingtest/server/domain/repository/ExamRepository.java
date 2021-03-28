package com.github.drivingtest.server.domain.repository;

import com.github.drivingtest.server.domain.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Integer>, JpaRepository<Exam, Integer> {
}

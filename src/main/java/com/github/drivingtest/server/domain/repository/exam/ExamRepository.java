package com.github.drivingtest.server.domain.repository.exam;

import com.github.drivingtest.server.domain.entity.exam.Exam;
import com.github.drivingtest.server.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Integer>, JpaRepository<Exam, Integer> {
    List<Exam> findAllByUser(User user);
}

package com.github.drivingtest.server.domain.repository.learn;

import com.github.drivingtest.server.domain.entity.learn.LearnPrimaryTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnPrimaryTaskRepository extends JpaRepository<LearnPrimaryTask, Integer> {
}

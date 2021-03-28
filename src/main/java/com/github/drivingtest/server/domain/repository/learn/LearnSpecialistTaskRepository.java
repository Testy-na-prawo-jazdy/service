package com.github.drivingtest.server.domain.repository.learn;

import com.github.drivingtest.server.domain.entity.learn.LearnSpecialistTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnSpecialistTaskRepository extends JpaRepository<LearnSpecialistTask, Integer> {
}

package com.github.drivingtest.server.domain.repository.learn;

import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.domain.entity.learn.LearnSpecialistTask;
import com.github.drivingtest.server.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearnSpecialistTaskRepository extends JpaRepository<LearnSpecialistTask, Integer> {
    Optional<LearnSpecialistTask> findLearnSpecialistTaskBySpecialistTaskAndUser(SpecialistTask specialistTask, User user);
}

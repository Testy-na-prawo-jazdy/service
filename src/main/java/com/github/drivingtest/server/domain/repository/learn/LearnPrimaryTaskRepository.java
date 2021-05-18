package com.github.drivingtest.server.domain.repository.learn;

import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.domain.entity.learn.LearnPrimaryTask;
import com.github.drivingtest.server.security.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearnPrimaryTaskRepository extends CrudRepository<LearnPrimaryTask, Integer>, JpaRepository<LearnPrimaryTask, Integer> {
    Optional<LearnPrimaryTask> findLearnPrimaryTaskByPrimaryTaskAndUser(PrimaryTask primaryTask, User user);
}

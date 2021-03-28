package com.github.drivingtest.server.domain.entity.learn;

import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.security.domain.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "LearnSpecialistTaskDetails")
public class LearnSpecialistTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToOne
    SpecialistTask specialistTask;

    boolean isCorrect;

    @OneToOne
    User user;
}

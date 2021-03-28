package com.github.drivingtest.server.domain.entity.learn;

import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.security.domain.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "LearnPrimaryTaskDetails")
public class LearnPrimaryTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToOne
    PrimaryTask primaryTask;

    boolean isCorrect;

    @OneToOne
    User user;
}

package com.github.drivingtest.server.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "ExamPrimaryTaskDetails")
public class ExamPrimaryTask {
    @Id
    int id;

    @OneToOne
    PrimaryTask primaryTask;

    boolean isCorrect;
}

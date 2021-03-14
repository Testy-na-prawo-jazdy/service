package com.github.drivingtest.server.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "ExamSpecialistTaskDetails")
public class ExamSpecialistTask {
    @Id
    int id;

    @OneToOne
    SpecialistTask specialistTask;

    boolean isCorrect;
}

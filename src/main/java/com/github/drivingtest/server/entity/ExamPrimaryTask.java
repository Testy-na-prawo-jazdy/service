package com.github.drivingtest.server.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "ExamPrimaryTaskDetails")
public class ExamPrimaryTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToOne
    PrimaryTask primaryTask;

    boolean isCorrect;
}

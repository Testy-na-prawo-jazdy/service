package com.github.drivingtest.server.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToMany
    List<ExamPrimaryTask> examPrimaryTasks;

    @OneToMany
    List<ExamSpecialistTask> examSpecialistTasks;

    String user;
}

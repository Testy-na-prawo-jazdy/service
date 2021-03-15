package com.github.drivingtest.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "Exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToMany
    @JoinColumn(name = "EXAM_ID")
    List<ExamPrimaryTask> examPrimaryTasks;

    @OneToMany
    @JoinColumn(name = "EXAM_ID")
    List<ExamSpecialistTask> examSpecialistTasks;

    String user;
}

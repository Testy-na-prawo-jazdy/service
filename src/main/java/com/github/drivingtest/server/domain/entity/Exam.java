package com.github.drivingtest.server.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

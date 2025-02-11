package com.github.drivingtest.server.domain.entity.exam;

import com.github.drivingtest.server.domain.entity.SpecialistTask;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "ExamSpecialistTaskDetails")
public class ExamSpecialistTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @OneToOne
    SpecialistTask specialistTask;

    String selectedAnswer;

    boolean isCorrect;
}

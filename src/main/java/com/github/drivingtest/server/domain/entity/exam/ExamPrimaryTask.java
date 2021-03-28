package com.github.drivingtest.server.domain.entity.exam;

import com.github.drivingtest.server.domain.entity.PrimaryTask;
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

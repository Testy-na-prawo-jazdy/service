package com.github.drivingtest.server.domain.entity.learn;

import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.security.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    String selectedAnswer;
}

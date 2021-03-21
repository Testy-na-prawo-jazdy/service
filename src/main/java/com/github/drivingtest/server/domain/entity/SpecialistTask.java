package com.github.drivingtest.server.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity(name = "SpecialistTask")
public class SpecialistTask {
    @Id
    int id;

    @Column(length = 512)
    String question;

    String answerA;

    String answerB;

    String answerC;

    String correctAnswer;

    String filename;

    int points;

    @ManyToMany
    List<Category> categories;
}

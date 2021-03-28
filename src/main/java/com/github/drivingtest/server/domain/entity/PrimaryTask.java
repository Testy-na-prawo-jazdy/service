package com.github.drivingtest.server.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "PrimaryTask")
public class PrimaryTask {
    @Id
    int id;

    @Column(length = 512)
    String question;

    boolean correctAnswer;

    String filename;

    int points;

    @ManyToMany
    List<Category> categories;
}

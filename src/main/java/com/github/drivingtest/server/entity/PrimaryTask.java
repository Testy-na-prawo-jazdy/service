package com.github.drivingtest.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "PrimaryTask")
public class PrimaryTask {
    @Id
    int id;

    String question;

    boolean correctAnswer;

    String filename;

    int points;

    @ManyToMany
    List<Category> categories;
}

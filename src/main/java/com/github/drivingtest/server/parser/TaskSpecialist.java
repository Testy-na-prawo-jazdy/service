package com.github.drivingtest.server.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class TaskSpecialist {
    private int taskId;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String correctAnswer;
    private String mediaName;
    private int points;
    private List<String> categories;
    private String blockName;
}

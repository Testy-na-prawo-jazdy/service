package com.github.drivingtest.server.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class TaskPrimary {
    private int taskId;
    private String question;
    private boolean correctAnswer;
    private String mediaName;
    private int points;
    private List<String> categories;
    private String blockName;
}

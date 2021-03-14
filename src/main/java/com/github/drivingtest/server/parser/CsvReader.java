package com.github.drivingtest.server.parser;

import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {

    private List<TaskSpecialist> taskSpecialistList;
    private List<TaskPrimary> taskPrimaryList;
    private final String PATH = "src/main/resources/BAZA_pytan.csv";

    public CsvReader() {
        taskSpecialistList = new ArrayList<>();
        taskPrimaryList = new ArrayList<>();
        readCsvFile(PATH);
    }

    public List<TaskSpecialist> getTaskSpecialistList() {
        return taskSpecialistList;
    }

    public List<TaskPrimary> getTaskPrimaryList() {
        return taskPrimaryList;
    }

    private void readCsvFile(String path) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] values = line.split(";");
                int taskId = Integer.parseInt(values[0]);
                String question = values[1];
                String answerA = values[2];
                String answerB = values[3];
                String answerC = values[4];
                String correctAnswer = values[5];
                String mediaName = values[6];
                String taskType = values[7];
                int points = Integer.parseInt(values[8]);
                String[] categories = values[9].split(",");
                String blockName = values[10];
                if (taskType.equals("PODSTAWOWY")) {
                    boolean correctAns = correctAnswer.equals("T");
                    taskPrimaryList.add(new TaskPrimary(taskId, question, correctAns, mediaName, points, categories, blockName));
                } else {
                    taskSpecialistList.add(new TaskSpecialist(taskId, question, answerA, answerB, answerC, correctAnswer, mediaName, points, categories, blockName));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

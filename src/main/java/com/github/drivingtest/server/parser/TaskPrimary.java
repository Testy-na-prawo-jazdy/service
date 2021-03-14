package com.github.drivingtest.server.parser;

import java.util.Arrays;

public class TaskPrimary {

    private int taskId;
    private String question;
    private boolean correctAnswer;
    private String mediaName;
    private int points;
    private String[] categories;
    private String blockName;

    @Override
    public String toString() {
        return "TaskSpecialist{" +
                "taskId=" + taskId +
                ", question='" + question + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", mediaName='" + mediaName + '\'' +
                ", points=" + points +
                ", categories=" + Arrays.toString(categories) +
                ", blockName='" + blockName + '\'' +
                '}';
    }

    public TaskPrimary(int taskId, String question, boolean correctAnswer, String mediaName, int points, String[] categories, String blockName) {
        this.taskId = taskId;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.mediaName = mediaName;
        this.points = points;
        this.categories = categories;
        this.blockName = blockName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }



}

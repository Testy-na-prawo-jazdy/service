package com.github.drivingtest.server.parser;

import java.util.Arrays;

public class TaskSpecialist {

    private int taskId;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String correctAnswer;
    private String mediaName;
    private int points;
    private String[] categories;
    private String blockName;

    @Override
    public String toString() {
        return "TaskPrimary{" +
                "taskId=" + taskId +
                ", question='" + question + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", mediaName='" + mediaName + '\'' +
                ", points=" + points +
                ", categories=" + Arrays.toString(categories) +
                ", blockName='" + blockName + '\'' +
                '}';
    }

    public TaskSpecialist(int taskId, String question, String answerA, String answerB, String answerC, String correctAnswer, String mediaName, int points, String[] categories, String blockName) {
        this.taskId = taskId;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.correctAnswer = correctAnswer;
        this.mediaName = mediaName;
        this.points = points;
        this.categories = categories;
        this.blockName = blockName;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getMediaName() {
        return mediaName;
    }

    public int getPoints() {
        return points;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getBlockName() {
        return blockName;
    }


    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }


}

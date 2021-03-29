package com.github.drivingtest.server.domain.mapper;

import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.domain.entity.exam.ExamPrimaryTask;
import com.github.drivingtest.server.domain.entity.exam.ExamSpecialistTask;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public List<ExamPrimaryTask> primaryTasksToExamTasks(List<PrimaryTask> primaryTaskList) {
        return primaryTaskList.stream().map(this::primaryTaskToExamTask).collect(Collectors.toList());
    }

    public ExamPrimaryTask primaryTaskToExamTask(PrimaryTask primaryTask) {
        ExamPrimaryTask examPrimaryTask = new ExamPrimaryTask();
        examPrimaryTask.setPrimaryTask(primaryTask);
        return examPrimaryTask;
    }

    public List<ExamSpecialistTask> specialistTasksToExamTasks(List<SpecialistTask> examSpecialistTasks) {
        return examSpecialistTasks.stream().map(this::specialistTaskToExamTask).collect(Collectors.toList());
    }

    public ExamSpecialistTask specialistTaskToExamTask(SpecialistTask specialistTask) {
        ExamSpecialistTask examSpecialistTask = new ExamSpecialistTask();
        examSpecialistTask.setSpecialistTask(specialistTask);
        return examSpecialistTask;
    }
}

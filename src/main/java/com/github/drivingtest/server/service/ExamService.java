package com.github.drivingtest.server.service;

import com.github.drivingtest.server.dto.GeneratedExam;
import com.github.drivingtest.server.entity.*;
import com.github.drivingtest.server.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExamService {

    private final PrimaryTaskRepository primaryTaskRepository;
    private final SpecialistTaskRepository specialistTaskRepository;
    private final ExamRepository examRepository;
    private final ExamPrimaryTaskRepository examPrimaryTaskRepository;
    private final ExamSpecialistTaskRepository examSpecialistTaskRepository;

    public ExamService(PrimaryTaskRepository primaryTaskRepository, SpecialistTaskRepository specialistTaskRepository, ExamRepository examRepository, ExamPrimaryTaskRepository examPrimaryTaskRepository, ExamSpecialistTaskRepository examSpecialistTaskRepository) {
        this.primaryTaskRepository = primaryTaskRepository;
        this.specialistTaskRepository = specialistTaskRepository;
        this.examRepository = examRepository;
        this.examPrimaryTaskRepository = examPrimaryTaskRepository;
        this.examSpecialistTaskRepository = examSpecialistTaskRepository;
    }

    public GeneratedExam doExam() {

        List<PrimaryTask> primaryTaskListAll = primaryTaskRepository.findAll();
        List<PrimaryTask> primaryTaskList20 = IntStream.range(0, 20).mapToObj(primaryTaskListAll::get).collect(Collectors.toList());

        List<ExamPrimaryTask> examPrimaryTasks = primaryTaskList20.stream().map(primaryTask -> {
            ExamPrimaryTask examPrimaryTask = new ExamPrimaryTask();
            examPrimaryTask.setPrimaryTask(primaryTask);
            return examPrimaryTask;
        }).collect(Collectors.toList());

        examPrimaryTaskRepository.saveAll(examPrimaryTasks);

        List<SpecialistTask> specialTaskListAll = specialistTaskRepository.findAll();
        List<SpecialistTask> specialTaskList12 = IntStream.range(0, 12).mapToObj(specialTaskListAll::get).collect(Collectors.toList());

        List<ExamSpecialistTask> examSpecialistTasks = specialTaskList12.stream().map(specialistTask -> {
            ExamSpecialistTask examSpecialistTask = new ExamSpecialistTask();
            examSpecialistTask.setSpecialistTask(specialistTask);
            return examSpecialistTask;
        }).collect(Collectors.toList());

        examSpecialistTaskRepository.saveAll(examSpecialistTasks);

        Exam exam = new Exam();
        exam.setExamPrimaryTasks(examPrimaryTasks);
        exam.setExamSpecialistTasks(examSpecialistTasks);

        examRepository.save(exam);

        return GeneratedExam.builder()
                .examId(1)
                .primaryTaskList(examPrimaryTasks)
                .specialistTaskList(examSpecialistTasks)
                .build();
    }
}

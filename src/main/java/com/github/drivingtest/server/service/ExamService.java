package com.github.drivingtest.server.service;

import com.github.drivingtest.server.domain.dto.form.request.ExamFormSubmit;
import com.github.drivingtest.server.domain.dto.form.response.ExamForm;
import com.github.drivingtest.server.domain.dto.form.response.ExamResult;
import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.domain.entity.exam.Exam;
import com.github.drivingtest.server.domain.entity.exam.ExamPrimaryTask;
import com.github.drivingtest.server.domain.entity.exam.ExamSpecialistTask;
import com.github.drivingtest.server.domain.mapper.ExamMapper;
import com.github.drivingtest.server.domain.mapper.TaskMapper;
import com.github.drivingtest.server.domain.repository.PrimaryTaskRepository;
import com.github.drivingtest.server.domain.repository.SpecialistTaskRepository;
import com.github.drivingtest.server.domain.repository.exam.ExamPrimaryTaskRepository;
import com.github.drivingtest.server.domain.repository.exam.ExamRepository;
import com.github.drivingtest.server.domain.repository.exam.ExamSpecialistTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExamService {

    private final PrimaryTaskRepository primaryTaskRepository;
    private final SpecialistTaskRepository specialistTaskRepository;
    private final ExamRepository examRepository;
    private final ExamPrimaryTaskRepository examPrimaryTaskRepository;
    private final ExamSpecialistTaskRepository examSpecialistTaskRepository;
    private final TaskMapper taskMapper;
    private final ExamMapper examMapper;

    public ExamService(PrimaryTaskRepository primaryTaskRepository, SpecialistTaskRepository specialistTaskRepository, ExamRepository examRepository, ExamPrimaryTaskRepository examPrimaryTaskRepository, ExamSpecialistTaskRepository examSpecialistTaskRepository, TaskMapper taskMapper, ExamMapper examMapper) {
        this.primaryTaskRepository = primaryTaskRepository;
        this.specialistTaskRepository = specialistTaskRepository;
        this.examRepository = examRepository;
        this.examPrimaryTaskRepository = examPrimaryTaskRepository;
        this.examSpecialistTaskRepository = examSpecialistTaskRepository;
        this.taskMapper = taskMapper;
        this.examMapper = examMapper;
    }

    public ExamForm startExam(CategoryEnum category) {

        List<PrimaryTask> primaryTaskList20 = get20PrimaryTasksByCategory(category);
        List<SpecialistTask> specialTaskList12 = get12SpecialistTasksByCategory(category);

        List<ExamPrimaryTask> examPrimaryTasks = taskMapper.primaryTasksToExamTasks(primaryTaskList20);
        List<ExamSpecialistTask> examSpecialistTasks = taskMapper.specialistTasksToExamTasks(specialTaskList12);

        examPrimaryTaskRepository.saveAll(examPrimaryTasks);
        examSpecialistTaskRepository.saveAll(examSpecialistTasks);

        Exam exam = Exam.builder()
                .examPrimaryTasks(examPrimaryTasks)
                .examSpecialistTasks(examSpecialistTasks)
                .build();

        Exam persistedExam = examRepository.save(exam);

        return examMapper.examToExamForm(persistedExam);
    }

    private List<PrimaryTask> get20PrimaryTasksByCategory(CategoryEnum category){
        List<PrimaryTask> primaryTaskList = primaryTaskRepository.findPrimaryTasksByCategoriesCategory(category);
        List<PrimaryTask> highPrimaryTaskList = primaryTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 3).collect(Collectors.toList()).subList(0, 10);
        List<PrimaryTask> midPrimaryTaskList = primaryTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 2).collect(Collectors.toList()).subList(0, 6);
        List<PrimaryTask> lowPrimaryTaskList = primaryTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 1).collect(Collectors.toList()).subList(0, 4);
        return Stream.of(highPrimaryTaskList, midPrimaryTaskList, lowPrimaryTaskList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<SpecialistTask> get12SpecialistTasksByCategory(CategoryEnum category){
        return specialistTaskRepository.findSpecialistTasksByCategoriesCategory(category).subList(0, 12);
    }

    public ExamResult finishExam(int id, ExamFormSubmit examFormSubmit) {

        Optional<Exam> optionalExam = examRepository.findById(id);

        if(optionalExam.isPresent()) {
            Exam exam = optionalExam.get();

            List<ExamPrimaryTask> examPrimaryTasks = exam.getExamPrimaryTasks().stream().peek(examPrimaryTask -> examFormSubmit.getPrimaryTaskList().stream()
                    .filter(efsPrimaryTask -> efsPrimaryTask.getId() == examPrimaryTask.getId())
                    .findFirst()
                    .ifPresent(efsPrimaryTask -> examPrimaryTask.setCorrect(examPrimaryTask.getPrimaryTask().isCorrectAnswer() == efsPrimaryTask.isChosenAnswer()))).collect(Collectors.toList());
            exam.setExamPrimaryTasks(examPrimaryTasks);

            List<ExamSpecialistTask> examSpecialistTasks = exam.getExamSpecialistTasks().stream().peek(examSpecialistTask -> examFormSubmit.getSpecialistTaskList().stream()
                    .filter(efsSpecialistTask -> efsSpecialistTask.getId() == examSpecialistTask.getId())
                    .findFirst()
                    .ifPresent(efsSpecialistTask -> examSpecialistTask.setCorrect(examSpecialistTask.getSpecialistTask().getCorrectAnswer().equals(efsSpecialistTask.getChosenAnswer())))).collect(Collectors.toList());
            exam.setExamSpecialistTasks(examSpecialistTasks);

            examRepository.save(exam);

            return examMapper.examToExamResult(exam);
        }

        return null;
    }
}

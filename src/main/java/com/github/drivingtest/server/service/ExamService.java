package com.github.drivingtest.server.service;

import com.github.drivingtest.server.domain.dto.form.request.ExamFormSubmit;
import com.github.drivingtest.server.domain.dto.form.response.ExamForm;
import com.github.drivingtest.server.domain.dto.form.response.ExamResult;
import com.github.drivingtest.server.domain.dto.history.ExamHistoryResponse;
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
import com.github.drivingtest.server.exception.ExamNotFoundException;
import com.github.drivingtest.server.security.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
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
    private final AuthService authService;

    public ExamService(PrimaryTaskRepository primaryTaskRepository, SpecialistTaskRepository specialistTaskRepository, ExamRepository examRepository, ExamPrimaryTaskRepository examPrimaryTaskRepository, ExamSpecialistTaskRepository examSpecialistTaskRepository, TaskMapper taskMapper, ExamMapper examMapper, AuthService authService) {
        this.primaryTaskRepository = primaryTaskRepository;
        this.specialistTaskRepository = specialistTaskRepository;
        this.examRepository = examRepository;
        this.examPrimaryTaskRepository = examPrimaryTaskRepository;
        this.examSpecialistTaskRepository = examSpecialistTaskRepository;
        this.taskMapper = taskMapper;
        this.examMapper = examMapper;
        this.authService = authService;
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
                .user(authService.getLoggedUser())
                .category(category)
                .date(new Date())
                .score(0)
                .build();

        Exam persistedExam = examRepository.save(exam);

        return examMapper.examToExamForm(persistedExam);
    }

    private List<PrimaryTask> get20PrimaryTasksByCategory(CategoryEnum category) {
        List<PrimaryTask> primaryTaskList = primaryTaskRepository.findPrimaryTasksByCategoriesCategory(category);
        List<PrimaryTask> highPrimaryTaskList = primaryTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 3).collect(Collectors.toList()).subList(0, 10);
        List<PrimaryTask> midPrimaryTaskList = primaryTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 2).collect(Collectors.toList()).subList(0, 6);
        List<PrimaryTask> lowPrimaryTaskList = primaryTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 1).collect(Collectors.toList()).subList(0, 4);
        return Stream.of(highPrimaryTaskList, midPrimaryTaskList, lowPrimaryTaskList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<SpecialistTask> get12SpecialistTasksByCategory(CategoryEnum category) {
        List<SpecialistTask> specialistTaskList = specialistTaskRepository.findSpecialistTasksByCategoriesCategory(category);
        List<SpecialistTask> highSpecialistTaskList = specialistTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 3).collect(Collectors.toList()).subList(0, 6);
        List<SpecialistTask> midSpecialistTaskList = specialistTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 2).collect(Collectors.toList()).subList(0, 4);
        List<SpecialistTask> lowSpecialistTaskList = specialistTaskList.stream().filter(primaryTask -> primaryTask.getPoints() == 1).collect(Collectors.toList()).subList(0, 2);
        return Stream.of(highSpecialistTaskList, midSpecialistTaskList, lowSpecialistTaskList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public ExamResult finishExam(int id, ExamFormSubmit examFormSubmit) {

        Optional<Exam> optionalExam = examRepository.findById(id);

        if (optionalExam.isPresent()) {
            Exam exam = optionalExam.get();

            List<ExamPrimaryTask> examPrimaryTasks = exam.getExamPrimaryTasks().stream().peek(examPrimaryTask -> examFormSubmit.getPrimaryTaskList().stream()
                    .filter(efsPrimaryTask -> efsPrimaryTask.getId() == examPrimaryTask.getId())
                    .findFirst()
                    .ifPresent(efsPrimaryTask -> {
                        examPrimaryTask.setSelectedAnswer(String.valueOf(efsPrimaryTask.isChosenAnswer()));
                        examPrimaryTask.setCorrect(examPrimaryTask.getPrimaryTask().isCorrectAnswer() == efsPrimaryTask.isChosenAnswer());
                    })).collect(Collectors.toList());
            exam.setExamPrimaryTasks(examPrimaryTasks);

            List<ExamSpecialistTask> examSpecialistTasks = exam.getExamSpecialistTasks().stream().peek(examSpecialistTask -> examFormSubmit.getSpecialistTaskList().stream()
                    .filter(efsSpecialistTask -> efsSpecialistTask.getId() == examSpecialistTask.getId())
                    .findFirst()
                    .ifPresent(efsSpecialistTask -> {
                        examSpecialistTask.setSelectedAnswer(efsSpecialistTask.getChosenAnswer());
                        examSpecialistTask.setCorrect(examSpecialistTask.getSpecialistTask().getCorrectAnswer().equals(efsSpecialistTask.getChosenAnswer()));
                    })).collect(Collectors.toList());
            exam.setExamSpecialistTasks(examSpecialistTasks);

            exam.setScore(countPoints(exam));

            examRepository.save(exam);

            return examMapper.examToExamResult(exam);
        }

        return null;
    }

    int countPoints(Exam exam) {
        List<ExamPrimaryTask> examPrimaryTasks = exam.getExamPrimaryTasks();
        List<ExamSpecialistTask> examSpecialistTasks = exam.getExamSpecialistTasks();

        int countPT = examPrimaryTasks.stream().filter(ExamPrimaryTask::isCorrect).map(examPrimaryTask -> examPrimaryTask.getPrimaryTask().getPoints()).reduce(0, Integer::sum);
        int countST = examSpecialistTasks.stream().filter(ExamSpecialistTask::isCorrect).map(examPrimaryTask -> examPrimaryTask.getSpecialistTask().getPoints()).reduce(0, Integer::sum);

        return countPT + countST;
    }

    public List<ExamHistoryResponse> getHistory() {
        return examRepository.findAllByUser(authService.getLoggedUser()).stream().map(examMapper::examToExamHistory).collect(Collectors.toList());
    }

    public ExamResult getExam(int id) {
        return examMapper.examToExamResult(examRepository.findById(id).orElseThrow(ExamNotFoundException::new));
    }
}

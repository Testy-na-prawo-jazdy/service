package com.github.drivingtest.server.configuration;

import com.github.drivingtest.server.domain.entity.Category;
import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.domain.repository.CategoryRepository;
import com.github.drivingtest.server.domain.repository.PrimaryTaskRepository;
import com.github.drivingtest.server.domain.repository.SpecialistTaskRepository;
import com.github.drivingtest.server.parser.CsvReader;
import com.github.drivingtest.server.parser.TaskPrimary;
import com.github.drivingtest.server.parser.TaskSpecialist;
import com.github.drivingtest.server.security.domain.dto.request.RegisterRequest;
import com.github.drivingtest.server.security.domain.entity.User;
import com.github.drivingtest.server.security.repository.UserRepository;
import com.github.drivingtest.server.security.service.AuthService;
import com.github.drivingtest.server.security.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataLoader implements ApplicationRunner {

    private final CsvReader csvReader;
    private final CategoryRepository categoryRepository;
    private final PrimaryTaskRepository primaryTaskRepository;
    private final SpecialistTaskRepository specialistTaskRepository;
    private final UserService userService;
    private final AuthService authService;

    public DataLoader(CsvReader csvReader, CategoryRepository categoryRepository, PrimaryTaskRepository primaryTaskRepository, SpecialistTaskRepository specialistTaskRepository, UserService userService, AuthService authService) {
        this.csvReader = csvReader;
        this.categoryRepository = categoryRepository;
        this.primaryTaskRepository = primaryTaskRepository;
        this.specialistTaskRepository = specialistTaskRepository;
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCategories();
        createPrimaryTasks();
        createSpecialistTasks();
        createDemoAccount();
    }

    private void createCategories() {
        Arrays.stream(CategoryEnum.values()).forEach(categoryEnum -> {
            Category category = new Category();
            category.setCategory(categoryEnum);
            categoryRepository.save(category);
        });
    }

    private void createPrimaryTasks() {
        List<TaskPrimary> taskPrimaryList = csvReader.getTaskPrimaryList();
        taskPrimaryList.forEach(taskPrimary -> {
            PrimaryTask primaryTask = new PrimaryTask();
            primaryTask.setId(taskPrimary.getTaskId());
            primaryTask.setQuestion(taskPrimary.getQuestion());
            primaryTask.setCorrectAnswer(taskPrimary.isCorrectAnswer());
            primaryTask.setCategories(getCategoriesFromString(taskPrimary.getCategories()));
            primaryTask.setFilename(taskPrimary.getMediaName());
            primaryTask.setPoints(taskPrimary.getPoints());
            primaryTaskRepository.save(primaryTask);
        });
    }

    private void createSpecialistTasks() {
        List<TaskSpecialist> taskSpecialistList = csvReader.getTaskSpecialistList();
        taskSpecialistList.forEach(taskSpecialist -> {
            SpecialistTask specialistTask = new SpecialistTask();
            specialistTask.setId(taskSpecialist.getTaskId());
            specialistTask.setQuestion(taskSpecialist.getQuestion());
            specialistTask.setAnswerA(taskSpecialist.getAnswerA());
            specialistTask.setAnswerB(taskSpecialist.getAnswerB());
            specialistTask.setAnswerC(taskSpecialist.getAnswerC());
            specialistTask.setCorrectAnswer(taskSpecialist.getCorrectAnswer());
            specialistTask.setCategories(getCategoriesFromString(taskSpecialist.getCategories()));
            specialistTask.setFilename(taskSpecialist.getMediaName());
            specialistTask.setPoints(taskSpecialist.getPoints());
            specialistTaskRepository.save(specialistTask);
        });
    }

    private List<Category> getCategoriesFromString(List<String> categoryStringList) {
        return categoryStringList.stream().map(s -> {
            Category category = new Category();
            category.setCategory(CategoryEnum.valueOf(s));
            return category;
        }).collect(Collectors.toList());
    }

    private void createDemoAccount() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("DEMO");
        registerRequest.setEmail("DEMO@LOCALHOST.COM");
        registerRequest.setPassword("DEMO123");
        authService.register(registerRequest);

        Optional<User> optionalUser = userService.findByUsername("DEMO");
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            authService.activateUser(user);
        }
    }
}

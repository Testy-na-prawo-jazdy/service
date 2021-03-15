package com.github.drivingtest.server.configuration;

import com.github.drivingtest.server.entity.Category;
import com.github.drivingtest.server.entity.CategoryEnum;
import com.github.drivingtest.server.entity.PrimaryTask;
import com.github.drivingtest.server.entity.SpecialistTask;
import com.github.drivingtest.server.parser.CsvReader;
import com.github.drivingtest.server.parser.TaskPrimary;
import com.github.drivingtest.server.parser.TaskSpecialist;
import com.github.drivingtest.server.repository.CategoryRepository;
import com.github.drivingtest.server.repository.PrimaryTaskRepository;
import com.github.drivingtest.server.repository.SpecialistTaskRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoader implements ApplicationRunner {

    private final CsvReader csvReader;
    private final CategoryRepository categoryRepository;
    private final PrimaryTaskRepository primaryTaskRepository;
    private final SpecialistTaskRepository specialistTaskRepository;

    public DataLoader(CsvReader csvReader, CategoryRepository categoryRepository, PrimaryTaskRepository primaryTaskRepository, SpecialistTaskRepository specialistTaskRepository) {
        this.csvReader = csvReader;
        this.categoryRepository = categoryRepository;
        this.primaryTaskRepository = primaryTaskRepository;
        this.specialistTaskRepository = specialistTaskRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCategories();
        createPrimaryTasks();
        createSpecialistTasks();
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
}

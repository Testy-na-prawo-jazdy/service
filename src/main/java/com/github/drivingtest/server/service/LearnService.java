package com.github.drivingtest.server.service;

import com.github.drivingtest.server.domain.dto.form.request.EFSPrimaryTask;
import com.github.drivingtest.server.domain.dto.form.request.EFSSpecialistTask;
import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.domain.entity.learn.LearnPrimaryTask;
import com.github.drivingtest.server.domain.entity.learn.LearnSpecialistTask;
import com.github.drivingtest.server.domain.repository.PrimaryTaskRepository;
import com.github.drivingtest.server.domain.repository.SpecialistTaskRepository;
import com.github.drivingtest.server.domain.repository.learn.LearnPrimaryTaskRepository;
import com.github.drivingtest.server.domain.repository.learn.LearnSpecialistTaskRepository;
import com.github.drivingtest.server.security.domain.entity.User;
import com.github.drivingtest.server.security.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LearnService {

    private final AuthService authService;
    private final PrimaryTaskRepository primaryTaskRepository;
    private final SpecialistTaskRepository specialistTaskRepository;
    private final LearnPrimaryTaskRepository learnPrimaryTaskRepository;
    private final LearnSpecialistTaskRepository learnSpecialistTaskRepository;

    public LearnService(AuthService authService, PrimaryTaskRepository primaryTaskRepository, SpecialistTaskRepository specialistTaskRepository, LearnPrimaryTaskRepository learnPrimaryTaskRepository, LearnSpecialistTaskRepository learnSpecialistTaskRepository) {
        this.authService = authService;
        this.primaryTaskRepository = primaryTaskRepository;
        this.specialistTaskRepository = specialistTaskRepository;
        this.learnPrimaryTaskRepository = learnPrimaryTaskRepository;
        this.learnSpecialistTaskRepository = learnSpecialistTaskRepository;
    }

    public Optional<PrimaryTask> findPrimaryTaskById(int id) {
        return primaryTaskRepository.findById(id);
    }

    public Optional<SpecialistTask> findSpecialistTaskById(int id) {
        return specialistTaskRepository.findById(id);
    }

    public PrimaryTask getRandomPrimaryTask(CategoryEnum category) {
        return primaryTaskRepository.findPrimaryTasksByCategoriesCategory(category).subList(0, 1).get(0);
    }

    public SpecialistTask getRandomSpecialistTask(CategoryEnum category) {
        return specialistTaskRepository.findSpecialistTasksByCategoriesCategory(category).subList(0, 1).get(0);
    }

    public PrimaryTask getUniquePrimaryTask(CategoryEnum category) {
        User user = authService.getLoggedUser();
        PrimaryTask primaryTask = primaryTaskRepository.findUniquePrimaryTasksByCategoriesCategory(category, user).get(0);

        Optional<LearnPrimaryTask> optionalLearnPrimaryTask = learnPrimaryTaskRepository.findLearnPrimaryTaskByPrimaryTaskAndUser(primaryTask, user);
        LearnPrimaryTask learnPrimaryTask;

        if (optionalLearnPrimaryTask.isPresent()) {
            learnPrimaryTask = optionalLearnPrimaryTask.get();
            learnPrimaryTask.setCorrect(false);
        } else {
            learnPrimaryTask = LearnPrimaryTask.builder()
                    .primaryTask(primaryTask)
                    .user(user)
                    .isCorrect(false)
                    .build();
        }

        learnPrimaryTaskRepository.save(learnPrimaryTask);

        return primaryTask;
    }

    public SpecialistTask getUniqueSpecialistTask(CategoryEnum category) {
        User user = authService.getLoggedUser();
        SpecialistTask specialistTask = specialistTaskRepository.findUniqueSpecialistTasksByCategoriesCategory(category, user).get(0);

        Optional<LearnSpecialistTask> optionalLearnSpecialistTask = learnSpecialistTaskRepository.findLearnSpecialistTaskBySpecialistTaskAndUser(specialistTask, user);
        LearnSpecialistTask learnSpecialistTask;

        if (optionalLearnSpecialistTask.isPresent()) {
            learnSpecialistTask = optionalLearnSpecialistTask.get();
            learnSpecialistTask.setCorrect(false);
        } else {
            learnSpecialistTask = LearnSpecialistTask.builder()
                    .specialistTask(specialistTask)
                    .user(user)
                    .isCorrect(false)
                    .build();
        }

        learnSpecialistTaskRepository.save(learnSpecialistTask);

        return specialistTask;
    }

    public LearnPrimaryTask finishPrimaryTask(int id, EFSPrimaryTask primaryTask) {
        User user = authService.getLoggedUser();

        Optional<LearnPrimaryTask> optionalLearnPrimaryTask = learnPrimaryTaskRepository.findById(id);

        if (optionalLearnPrimaryTask.isPresent()) {
            LearnPrimaryTask learnPrimaryTask = optionalLearnPrimaryTask.get();

            if (learnPrimaryTask.getUser().equals(user)) {
                learnPrimaryTask.setCorrect(learnPrimaryTask.getPrimaryTask().isCorrectAnswer() == primaryTask.isChosenAnswer());
                learnPrimaryTaskRepository.save(learnPrimaryTask);
                return learnPrimaryTask;
            }
        }

        return null;
    }

    public LearnSpecialistTask finishSpecialistTask(int id, EFSSpecialistTask specialistTask) {
        User user = authService.getLoggedUser();

        Optional<LearnSpecialistTask> optionalLearnSpecialistTask = learnSpecialistTaskRepository.findById(id);

        if (optionalLearnSpecialistTask.isPresent()) {
            LearnSpecialistTask learnSpecialistTask = optionalLearnSpecialistTask.get();

            if (learnSpecialistTask.getUser().equals(user)) {
                learnSpecialistTask.setCorrect(learnSpecialistTask.getSpecialistTask().getCorrectAnswer().equals(specialistTask.getChosenAnswer()));
                learnSpecialistTaskRepository.save(learnSpecialistTask);
                return learnSpecialistTask;
            }
        }

        return null;
    }
}

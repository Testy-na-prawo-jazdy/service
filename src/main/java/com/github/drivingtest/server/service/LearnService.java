package com.github.drivingtest.server.service;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.domain.entity.SpecialistTask;
import com.github.drivingtest.server.domain.repository.PrimaryTaskRepository;
import com.github.drivingtest.server.domain.repository.SpecialistTaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LearnService {

    private final PrimaryTaskRepository primaryTaskRepository;
    private final SpecialistTaskRepository specialistTaskRepository;

    public LearnService(PrimaryTaskRepository primaryTaskRepository, SpecialistTaskRepository specialistTaskRepository) {
        this.primaryTaskRepository = primaryTaskRepository;
        this.specialistTaskRepository = specialistTaskRepository;
    }

    public Optional<PrimaryTask> findPrimaryTaskById(int id) {
        return primaryTaskRepository.findById(id);
    }

    public Optional<SpecialistTask> findSpecialistTaskById(int id) {
        return specialistTaskRepository.findById(id);
    }

    public PrimaryTask getRandomPrimaryTask(CategoryEnum category){
        return primaryTaskRepository.findPrimaryTasksByCategoriesCategory(category).subList(0, 1).get(0);
    }

    public SpecialistTask getRandomSpecialistTask(CategoryEnum category){
        return specialistTaskRepository.findSpecialistTasksByCategoriesCategory(category).subList(0, 1).get(0);
    }

    public PrimaryTask getUniquePrimaryTask() {
        return null;
    }
}

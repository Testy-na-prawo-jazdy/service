package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.PrimaryTask;
import com.github.drivingtest.server.service.LearnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LearnController {

    private final LearnService learnService;

    public LearnController(LearnService learnService) {
        this.learnService = learnService;
    }

    @GetMapping("/xd/test")
    ResponseEntity<PrimaryTask> getUniquePrimaryTask() {
        return new ResponseEntity<>(learnService.getUniquePrimaryTask(CategoryEnum.B), HttpStatus.OK);
    }
}

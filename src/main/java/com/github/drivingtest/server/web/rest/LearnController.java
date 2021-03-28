package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.service.LearnService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LearnController {

    private final LearnService learnService;

    public LearnController(LearnService learnService) {
        this.learnService = learnService;
    }

}

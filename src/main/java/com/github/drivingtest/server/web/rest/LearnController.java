package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.domain.dto.form.request.EFSPrimaryTask;
import com.github.drivingtest.server.domain.dto.form.request.EFSSpecialistTask;
import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.domain.entity.learn.LearnPrimaryTask;
import com.github.drivingtest.server.domain.entity.learn.LearnSpecialistTask;
import com.github.drivingtest.server.service.LearnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LearnController {

    public static final String PATH_POST_LEARN_PRIMARY_TASK_START = "/learn/primaryTask/start/{category}";
    public static final String PATH_POST_LEARN_PRIMARY_TASK_FINISH = "/learn/primaryTask/finish/{id}";
    public static final String PATH_POST_LEARN_SPECIALIST_TASK_START = "/learn/specialistTask/start/{category}";
    public static final String PATH_POST_LEARN_SPECIALIST_TASK_FINISH = "/learn/specialistTask/finish/{id}";

    private final LearnService learnService;

    public LearnController(LearnService learnService) {
        this.learnService = learnService;
    }

    @PostMapping(PATH_POST_LEARN_PRIMARY_TASK_START)
    ResponseEntity<LearnPrimaryTask> startLearnPrimaryTask(@PathVariable String category) {
        return new ResponseEntity<>(learnService.getUniquePrimaryTask(CategoryEnum.valueOf(category)), HttpStatus.OK);
    }

    @PostMapping(PATH_POST_LEARN_PRIMARY_TASK_FINISH)
    ResponseEntity<LearnPrimaryTask> finishLearnPrimaryTask(@PathVariable int id, @RequestBody EFSPrimaryTask primaryTask) {
        return new ResponseEntity<>(learnService.finishPrimaryTask(id, primaryTask), HttpStatus.OK);
    }

    @PostMapping(PATH_POST_LEARN_SPECIALIST_TASK_START)
    ResponseEntity<LearnSpecialistTask> startLearnSpecialistTask(@PathVariable String category) {
        return new ResponseEntity<>(learnService.getUniqueSpecialistTask(CategoryEnum.valueOf(category)), HttpStatus.OK);
    }

    @PostMapping(PATH_POST_LEARN_SPECIALIST_TASK_FINISH)
    ResponseEntity<LearnSpecialistTask> finishLearnSpecialistTask(@PathVariable int id, @RequestBody EFSSpecialistTask specialistTask) {
        return new ResponseEntity<>(learnService.finishSpecialistTask(id, specialistTask), HttpStatus.OK);
    }
}

package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.domain.dto.ExamResponse;
import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    public static final String PATH_POST_START_EXAM = "/exam/start/{category}";

    private final ExamService examService;

    public TestController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping(PATH_POST_START_EXAM)
    public ResponseEntity<ExamResponse> doExam(@PathVariable("category") CategoryEnum category) {
        return new ResponseEntity<>(examService.startExam(category), HttpStatus.OK);
    }
}

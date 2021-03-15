package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.dto.GeneratedExam;
import com.github.drivingtest.server.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ExamService examService;

    public TestController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("exam")
    public ResponseEntity<GeneratedExam> doExam() {
        return new ResponseEntity<>(examService.doExam(), HttpStatus.OK);
    }
}

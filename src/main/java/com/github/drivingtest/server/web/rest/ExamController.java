package com.github.drivingtest.server.web.rest;

import com.github.drivingtest.server.domain.dto.form.request.ExamFormSubmit;
import com.github.drivingtest.server.domain.dto.form.response.ExamForm;
import com.github.drivingtest.server.domain.dto.form.response.ExamResult;
import com.github.drivingtest.server.domain.entity.CategoryEnum;
import com.github.drivingtest.server.service.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExamController {

    public static final String PATH_POST_START_EXAM = "/exam/start/{category}";
    public static final String PATH_POST_FINISH_EXAM = "/exam/finish/{id}";

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping(PATH_POST_START_EXAM)
    public ResponseEntity<ExamForm> startExam(@PathVariable("category") CategoryEnum category) {
        return new ResponseEntity<>(examService.startExam(category), HttpStatus.OK);
    }

    @PostMapping(PATH_POST_FINISH_EXAM)
    public ResponseEntity<ExamResult> finishExam(@PathVariable int id, @RequestBody ExamFormSubmit examFormSubmit) {
        return new ResponseEntity<>(examService.finishExam(id, examFormSubmit), HttpStatus.OK);
    }
}

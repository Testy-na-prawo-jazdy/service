package com.github.drivingtest.server.web;

import com.github.drivingtest.server.exception.ExamNotFoundException;
import com.github.drivingtest.server.security.domain.dto.error.ErrorResponse;
import com.github.drivingtest.server.security.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(ExamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = "Exam not found";
        return new ErrorResponse(HttpStatus.NOT_FOUND, errorMessage);
    }
}

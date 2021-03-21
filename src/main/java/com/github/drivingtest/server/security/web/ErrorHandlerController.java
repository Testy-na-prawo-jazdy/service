package com.github.drivingtest.server.security.web;

import com.github.drivingtest.server.security.domain.dto.error.ErrorResponse;
import com.github.drivingtest.server.security.domain.dto.error.ValidationErrorResponse;
import com.github.drivingtest.server.security.exception.InvalidRefreshTokenException;
import com.github.drivingtest.server.security.exception.UserAlreadyExistsException;
import com.github.drivingtest.server.security.exception.UserNotFoundException;
import com.github.drivingtest.server.security.exception.WrongPasswordException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> fieldsErrors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        String errorMessage = "Object validation failed. Check fields errors.";
        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, fieldsErrors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = "User not found";
        return new ErrorResponse(HttpStatus.NOT_FOUND, errorMessage);
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleWrongPasswordException(WrongPasswordException ex) {
        String errorMessage = "Incorrect login details";
        return new ErrorResponse(HttpStatus.FORBIDDEN, errorMessage);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
        String errorMessage = "Invalid refresh token";
        return new ErrorResponse(HttpStatus.FORBIDDEN, errorMessage);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        String errorMessage = "User already exists";
        return new ErrorResponse(HttpStatus.CONFLICT, errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        String errorMessage = "Access denied";
        return new ErrorResponse(HttpStatus.FORBIDDEN, errorMessage);
    }
}
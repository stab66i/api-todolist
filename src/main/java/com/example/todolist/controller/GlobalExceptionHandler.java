package com.example.todolist.controller;

import com.example.todolist.exception.TaskNotFound;
import com.example.todolist.model.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<AppError> taskNotFound(TaskNotFound ex) {
        return new ResponseEntity<>(new AppError(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}

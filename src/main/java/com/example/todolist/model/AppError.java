package com.example.todolist.model;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class AppError {
    private String message;
    private HttpStatus code;
}

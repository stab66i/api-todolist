package com.example.todolist.exception;

public class TaskNotFound extends Exception {
    private String message;

    public TaskNotFound(String message) {
        super(message);
    }
}

package com.example.todolist.model;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private String description;
    private Boolean completed;
}

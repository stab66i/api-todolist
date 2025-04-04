package com.example.todolist.model;

import lombok.Data;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
}

package com.example.todolist.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 500, message = "Description can't exceed 500 characters")
    private String description;
    private Boolean completed;
}

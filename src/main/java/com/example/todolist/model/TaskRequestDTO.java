package com.example.todolist.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {
    @Schema(description = "Название задачи", example = "Купить молоко")
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Описание задачи", example = "Простоквашино")
    @Size(max = 500, message = "Description can't exceed 500 characters")
    private String description;

    @Schema(description = "Статус выполнения", example = "false")
    private Boolean completed;
}

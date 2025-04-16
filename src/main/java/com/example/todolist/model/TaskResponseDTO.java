package com.example.todolist.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    @Schema(description = "Индекс задачи", example = "1")
    private Long id;

    @Schema(description = "Название задачи", example = "Купить молоко")
    private String title;

    @Schema(description = "Описание задачи", example = "Простоквашино")
    private String description;

    @Schema(description = "Статус выполнения задачи", example = "false")
    private Boolean completed;
}

package com.example.todolist.mapper;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskResponseDTO;

public class TaskMapper {
    public static TaskResponseDTO toTaskResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCompleted()
        );
    }
}

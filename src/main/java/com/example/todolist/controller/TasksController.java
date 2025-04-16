package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskRequestDTO;
import com.example.todolist.model.TaskResponseDTO;
import com.example.todolist.service.TasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/todos")
@Tag(name = "Задачи", description = "CRUD-операции с задачами")
public class TasksController {
    private final TasksService tasksService;

    @Operation(summary = "Создать новую задачу")
    @PostMapping()
    public TaskResponseDTO createTask(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        return tasksService.createTask(taskRequestDTO);
    }

    @Operation(summary = "Получить список всех задач")
    @GetMapping()
    public List<TaskResponseDTO> getTasks() {
        return tasksService.getTasks();
    }

    @Operation(summary = "Получить задачу по id")
    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return tasksService.getTaskById(id);
    }

    @Operation(summary = "Обновить задачу")
    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        return tasksService.updateTask(id, taskRequestDTO);
    }

    @Operation(summary = "Удалить задачу")
    @DeleteMapping("/{id}")
    public TaskResponseDTO deleteTask(@PathVariable Long id) {
        return tasksService.deleteTask(id);
    }

    @Operation(summary = "Пометить задачу выполненной")
    @PatchMapping("/{id}/complete")
    public TaskResponseDTO completeTask(@PathVariable Long id) {
        return tasksService.completeTask(id);
    }

    @Operation(summary = "Отфильтровать задачи по статусу")
    @GetMapping("/filter")
    public List<TaskResponseDTO> getTasksByCompleted(@RequestParam Boolean completed) {
        return tasksService.getTasksByCompleted(completed);
    }
}

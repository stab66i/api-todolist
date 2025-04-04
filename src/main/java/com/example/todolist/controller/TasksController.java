package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskRequestDTO;
import com.example.todolist.model.TaskResponseDTO;
import com.example.todolist.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/todos")
public class TasksController {
    private final TasksService tasksService;

    @PostMapping()
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        return tasksService.createTask(taskRequestDTO);
    }

    @GetMapping()
    public List<TaskResponseDTO> getTasks() {
        return tasksService.getTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return tasksService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        return tasksService.updateTask(id, taskRequestDTO);
    }

    @DeleteMapping("/{id}")
    public TaskResponseDTO deleteTask(@PathVariable Long id) {
        return tasksService.deleteTask(id);
    }

    @PatchMapping("/{id}/complete")
    public TaskResponseDTO completeTask(@PathVariable Long id) {
        return tasksService.completeTask(id);
    }
}

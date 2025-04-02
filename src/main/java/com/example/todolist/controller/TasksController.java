package com.example.todolist.controller;

import com.example.todolist.model.Task;
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
    public Task createTask(@RequestBody Task task) {
        return tasksService.createTask(task);
    }

    @GetMapping()
    public List<Task> getTasks() {
        return tasksService.getTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return tasksService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return tasksService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
    }

    @PatchMapping("/{id}/complete")
    public Task completeTask(@PathVariable Long id) {
        return tasksService.completeTask(id);
    }
}

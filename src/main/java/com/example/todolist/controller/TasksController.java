package com.example.todolist.controller;

import com.example.todolist.repository.TasksRepository;
import com.example.todolist.service.TasksService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/todos")
public class TasksController {
    private final TasksService tasksService;

}

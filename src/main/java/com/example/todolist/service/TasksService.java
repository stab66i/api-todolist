package com.example.todolist.service;

import com.example.todolist.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
}

package com.example.todolist.service;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;

    public Task createTask(Task task) {
        return tasksRepository.saveAndFlush(task);
    }

    public List<Task> getTasks() {
        return tasksRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return tasksRepository.findById(id).get();
    }

    public Task updateTask(Long id, Task task) {
        Task taskToUpdate = tasksRepository.findById(id).get();
        taskToUpdate.setCompleted(task.getCompleted());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setTitle(task.getTitle());
        return tasksRepository.saveAndFlush(taskToUpdate);
    }

    public void deleteTask(Long id) {
        tasksRepository.deleteById(id);
    }

    public Task completeTask(Long id) {
        Task taskToUpdate = tasksRepository.findById(id).get();
        taskToUpdate.setCompleted(true);
        return tasksRepository.saveAndFlush(taskToUpdate);
    }
}

package com.example.todolist.service;

import com.example.todolist.exception.TaskNotFound;
import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskRequestDTO;
import com.example.todolist.model.TaskResponseDTO;
import com.example.todolist.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setCompleted(taskRequestDTO.getCompleted());

        task = tasksRepository.saveAndFlush(task);

        return TaskMapper.toTaskResponseDTO(task);
    }

    public List<TaskResponseDTO> getTasks() {
        List<Task> tasks = tasksRepository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFound("Задач нет");
        }
        return tasks
                .stream()
                .map(TaskMapper::toTaskResponseDTO)
                .toList();
    }

    public TaskResponseDTO getTaskById(Long id) {
        return tasksRepository.findById(id)
                .map(TaskMapper::toTaskResponseDTO)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task taskToUpdate = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));
        taskToUpdate.setCompleted(taskRequestDTO.getCompleted());
        taskToUpdate.setDescription(taskRequestDTO.getDescription());
        taskToUpdate.setTitle(taskRequestDTO.getTitle());

        return TaskMapper.toTaskResponseDTO(tasksRepository.saveAndFlush(taskToUpdate));
    }

    public TaskResponseDTO deleteTask(Long id) {
        Task taskToDelete = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));

        tasksRepository.deleteById(id);

        return TaskMapper.toTaskResponseDTO(taskToDelete);
    }

    public TaskResponseDTO completeTask(Long id) {
        Task taskToUpdate = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));
        taskToUpdate.setCompleted(true);

        return TaskMapper.toTaskResponseDTO(tasksRepository.saveAndFlush(taskToUpdate));
    }

    public List<TaskResponseDTO> getTasksByCompleted(Boolean completed) {
        List<Task> tasks =  tasksRepository.findAllByCompleted(completed);

        if (tasks.isEmpty()) {
            throw new TaskNotFound("Задач с таким параметром не найдено");
        }

        return tasks
                .stream()
                .map(TaskMapper::toTaskResponseDTO)
                .toList();
    }
}

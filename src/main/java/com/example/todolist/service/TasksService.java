package com.example.todolist.service;

import com.example.todolist.exception.TaskNotFound;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskRequestDTO;
import com.example.todolist.model.TaskResponseDTO;
import com.example.todolist.repository.TasksRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;

    public TaskResponseDTO toTaskResponseDTO(Task task) {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setDescription(task.getDescription());
        taskResponseDTO.setCompleted(task.getCompleted());
        return taskResponseDTO;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setCompleted(taskRequestDTO.getCompleted());

        tasksRepository.saveAndFlush(task);

        return toTaskResponseDTO(task);
    }

    public List<TaskResponseDTO> getTasks() {
        List<Task> tasks = tasksRepository.findAll();
        List<TaskResponseDTO> tasksResponseDTOS = new ArrayList<>();
        for (Task task : tasks) {
            tasksResponseDTOS.add(toTaskResponseDTO(task));
        }
        return tasksResponseDTOS;
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));
        return toTaskResponseDTO(task);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task taskToUpdate = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));
        taskToUpdate.setCompleted(taskRequestDTO.getCompleted());
        taskToUpdate.setDescription(taskRequestDTO.getDescription());
        taskToUpdate.setTitle(taskRequestDTO.getTitle());

        tasksRepository.saveAndFlush(taskToUpdate);

        return toTaskResponseDTO(taskToUpdate);
    }

    public TaskResponseDTO deleteTask(Long id) {
        Task taskToDelete = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));
        tasksRepository.deleteById(id);

        return toTaskResponseDTO(taskToDelete);
    }

    public TaskResponseDTO completeTask(Long id) {
        Task taskToUpdate = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound("Задача с id " + id + " не найдена"));;
        taskToUpdate.setCompleted(true);

        tasksRepository.saveAndFlush(taskToUpdate);

        return toTaskResponseDTO(taskToUpdate);
    }
}

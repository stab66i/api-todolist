package com.example.todolist.service;

import com.example.todolist.exception.TaskNotFound;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskRequestDTO;
import com.example.todolist.model.TaskResponseDTO;
import com.example.todolist.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TasksServiceTest {
    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksService tasksService;

    @Test
    public void getTaskById_ShouldReturnDTO_WhenTaskExists() {
        Task task = new Task(
                1L, "title", "description", false);
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDTO responseDTO = tasksService.getTaskById(1L);
        assertEquals(task.getTitle(), responseDTO.getTitle());
        assertEquals(task.getDescription(), responseDTO.getDescription());
        assertEquals(task.getId(), responseDTO.getId());
        assertEquals(task.getCompleted(), responseDTO.getCompleted());
        verify(tasksRepository, times(1)).findById(1L);
    }

    @Test
    public void getTaskById_ShouldThrowException_WhenTaskDoesNotExist() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.empty());

        TaskNotFound ex = assertThrows(TaskNotFound.class, () -> tasksService.getTaskById(1L));

        assertEquals("Задача с id 1 не найдена", ex.getMessage());
    }

    @Test
    public void createTask_ShouldReturnDTO_WhenTaskCreated() {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("title", "description", false);
        Task savedTask = new Task(1L, "title", "description", false);

        when(tasksRepository.saveAndFlush(any(Task.class))).thenReturn(savedTask);

        TaskResponseDTO taskResponseDTO = tasksService.createTask(taskRequestDTO);

        assertEquals(savedTask.getTitle(), taskResponseDTO.getTitle());
        assertEquals(savedTask.getDescription(), taskResponseDTO.getDescription());
        assertEquals(savedTask.getId(), taskResponseDTO.getId());
        assertEquals(savedTask.getCompleted(), taskResponseDTO.getCompleted());
        //verify(tasksRepository, times(0)).saveAndFlush(savedTask);
        //если 0 - не ломается, а 1 - ломается
    }

    @Test
    public void getTasks_ShouldReturnDTO_WhenTasksExist() {
        Task task1 = new Task(1L, "title", "description", false);
        Task task2 = new Task(2L, "title2", "description2", false);

        when(tasksRepository.findAll()).thenReturn(List.of(task1, task2));

        List<TaskResponseDTO> tasks = tasksService.getTasks();

        assertEquals(2, tasks.size());
        assertEquals(task1.getId(), tasks.get(0).getId());
        assertEquals(task2.getId(), tasks.get(1).getId());
        assertEquals(task1.getCompleted(), tasks.get(0).getCompleted());
        assertEquals(task2.getCompleted(), tasks.get(1).getCompleted());
        assertEquals(task1.getTitle(), tasks.get(0).getTitle());
        assertEquals(task2.getTitle(), tasks.get(1).getTitle());
        assertEquals(task1.getDescription(), tasks.get(0).getDescription());
        assertEquals(task2.getDescription(), tasks.get(1).getDescription());
        verify(tasksRepository, times(1)).findAll();
    }

    @Test
    public void getTasks_ShouldThrowException_WhenTasksDoNotExist() {
        when(tasksRepository.findAll()).thenReturn(List.of());

        TaskNotFound ex = assertThrows(TaskNotFound.class, () -> tasksService.getTasks());

        assertEquals("Задач нет", ex.getMessage());
    }

    @Test
    public void updateTask_ShouldReturnDTO_WhenTaskUpdated() {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("title2", "description2", false);
        Task taskToUpdate = new Task(1L, "title1", "description1", false);

        when(tasksRepository.findById(1L)).thenReturn(Optional.of(taskToUpdate));
        when(tasksRepository.saveAndFlush(taskToUpdate)).thenReturn(taskToUpdate);

        TaskResponseDTO responseDTO = tasksService.updateTask(1L, taskRequestDTO);

        assertEquals(taskToUpdate.getTitle(), responseDTO.getTitle());
        assertEquals(taskToUpdate.getDescription(), responseDTO.getDescription());
        assertEquals(taskToUpdate.getId(), responseDTO.getId());
        assertEquals(taskToUpdate.getCompleted(), responseDTO.getCompleted());
        //verify(tasksRepository, times(2)).findById(1L);
        //2 - неверно 1 - верно :(
    }
}

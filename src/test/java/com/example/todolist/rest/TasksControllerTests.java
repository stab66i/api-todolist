package com.example.todolist.rest;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskRequestDTO;
import com.example.todolist.repository.TasksRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TasksControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TasksRepository repository;

    private TasksControllerTestData testData;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        Task task = new Task("Пример задачи", "Описание задачи", false);
        repository.save(task);
    }

    @Test
    public void testGetTasks_Success() throws Exception {
        mockMvc.perform(get("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Пример задачи"))
                .andExpect(jsonPath("$[0].description").value("Описание задачи"))
                .andExpect(jsonPath("$[0].completed").value(false));

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testGetTasks_NotFound() throws Exception {
        repository.deleteAll();
        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTask_Success() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("Новая задача", "Новое описание", false);
        String json = objectMapper.writeValueAsString(taskRequestDTO);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Новая задача"))
                .andExpect(jsonPath("$.description").value("Новое описание"))
                .andExpect(jsonPath("$.completed").value(false));
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void testCreateTask_EmptyTitle() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("", "Новое описание", false);
        String json = objectMapper.writeValueAsString(taskRequestDTO);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testGetTaskById_Success() throws Exception {
        mockMvc.perform(get("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testGetTaskById_NotFound() throws Exception {
        repository.deleteAll();
        mockMvc.perform(get("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testUpdateTask_Success() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("Новая задача", "Новое описание", false);
        String json = objectMapper.writeValueAsString(taskRequestDTO);

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Новая задача"))
                .andExpect(jsonPath("$.description").value("Новое описание"))
                .andExpect(jsonPath("$.completed").value(false));

        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testUpdateTask_NotFound() throws Exception {
        repository.deleteAll();
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("Новая задача", "Новое описание", false);
        String json = objectMapper.writeValueAsString(taskRequestDTO);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testUpdateTask_EmptyTitle() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("", "Новое описание", false);
        String json = objectMapper.writeValueAsString(taskRequestDTO);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void testDeleteTask_Success() throws Exception {
        Task task = new Task("Задача", "Описание", false);
        Task savedTask = repository.save(task);

        mockMvc.perform(delete("/api/todos/" + savedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTask.getId()));

        assertThat(repository.findById(savedTask.getId())).isEmpty();
    }

    @Test
    public void testDeleteTask_NotFound() throws Exception {
        repository.deleteAll();
        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
package uk.gov.hmcts.reform.dev.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTasks_ShouldReturnAllTasks() throws Exception {
        // Arrange
        Task task1 = new Task("Task 1", "Description 1", "PENDING", LocalDateTime.now().plusDays(1));
        task1.setId(1L);
        Task task2 = new Task("Task 2", "Description 2", "IN_PROGRESS", LocalDateTime.now().plusDays(2));
        task2.setId(2L);
        
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void getTaskById_WhenTaskExists_ShouldReturnTask() throws Exception {
        // Arrange
        Task task = new Task("Task 1", "Description 1", "PENDING", LocalDateTime.now().plusDays(1));
        task.setId(1L);
        
        when(taskService.getTaskById(1L)).thenReturn(task);

        // Act & Assert
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    void createTask_WithValidData_ShouldReturnCreatedTask() throws Exception {
        // Arrange
        Task task = new Task("New Task", "New Description", "TODO", LocalDateTime.now().plusDays(3));
        task.setId(1L);
        
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void updateTaskStatus_WithValidStatus_ShouldReturnUpdatedTask() throws Exception {
        // Arrange
        Task task = new Task("Task 1", "Description 1", "DONE", LocalDateTime.now().plusDays(1));
        task.setId(1L);
        
        Map<String, String> statusUpdate = new HashMap<>();
        statusUpdate.put("status", "DONE");
        
        when(taskService.updateTaskStatus(eq(1L), eq("DONE"))).thenReturn(task);

        // Act & Assert
        mockMvc.perform(patch("/api/tasks/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("DONE"));
    }
}
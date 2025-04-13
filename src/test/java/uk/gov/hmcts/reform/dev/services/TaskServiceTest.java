package uk.gov.hmcts.reform.dev.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task("Task 1", "Description 1", "PENDING", LocalDateTime.now().plusDays(1));
        task1.setId(1L);

        task2 = new Task("Task 2", "Description 2", "IN_PROGRESS", LocalDateTime.now().plusDays(2));
        task2.setId(2L);
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_WhenTaskExists_ShouldReturnTask() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        // Act
        Task result = taskService.getTaskById(1L);

        // Assert
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_WhenTaskDoesNotExist_ShouldThrowException() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> taskService.getTaskById(99L));
        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        // Arrange
        Task newTask = new Task("New Task", "New Description", "TODO", LocalDateTime.now().plusDays(3));
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        // Act
        Task result = taskService.createTask(newTask);

        // Assert
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTaskStatus_WhenTaskExists_ShouldUpdateStatus() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Task result = taskService.updateTaskStatus(1L, "DONE");

        // Assert
        assertEquals("DONE", result.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
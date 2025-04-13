package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.TaskService;

import java.util.List;
import java.util.Map;

/**
 * REST controller for task management operations.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    /**
     * Constructor with TaskService dependency.
     *
     * @param taskService Task service
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    /**
     * Get all tasks.
     *
     * @return List of all tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    
    /**
     * Get task by ID.
     *
     * @param id Task ID
     * @return Task if found, 404 otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Create a new task.
     *
     * @param task Task details
     * @return Created task
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // Basic validation
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (task.getDueDate() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    
    /**
     * Update an existing task.
     *
     * @param id Task ID
     * @param task Updated task details
     * @return Updated task if found, 404 otherwise
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            // Basic validation
            if (task.getTitle() == null || task.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (task.getStatus() == null || task.getStatus().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (task.getDueDate() == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Update task status.
     *
     * @param id Task ID
     * @param statusUpdate Status update object containing new status
     * @return Updated task if found, 404 otherwise
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        try {
            String status = statusUpdate.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Task updatedTask = taskService.updateTaskStatus(id, status);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete a task.
     *
     * @param id Task ID
     * @return 204 No Content if successful, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
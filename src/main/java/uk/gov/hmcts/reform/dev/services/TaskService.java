package uk.gov.hmcts.reform.dev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.List;

/**
 * Service for task management operations.
 */
@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    /**
     * Constructor with TaskRepository dependency.
     *
     * @param taskRepository Task repository
     */
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    /**
     * Get all tasks.
     *
     * @return List of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    /**
     * Get task by ID.
     *
     * @param id Task ID
     * @return Task if found
     * @throws RuntimeException if task not found
     */
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }
    
    /**
     * Create a new task.
     *
     * @param task Task to create
     * @return Created task
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    /**
     * Update an existing task.
     *
     * @param id Task ID
     * @param taskDetails Task details to update
     * @return Updated task
     * @throws RuntimeException if task not found
     */
    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);
        
        // Update fields
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setDueDate(taskDetails.getDueDate());
        
        return taskRepository.save(task);
    }
    
    /**
     * Update task status.
     *
     * @param id Task ID
     * @param status New status
     * @return Updated task
     * @throws RuntimeException if task not found
     */
    public Task updateTaskStatus(Long id, String status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }
    
    /**
     * Delete a task.
     *
     * @param id Task ID
     * @throws RuntimeException if task not found
     */
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }
}
package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository for Task entity operations.
 */
@Repository
public class TaskRepository {
    
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    /**
     * Find all tasks.
     *
     * @return List of all tasks
     */
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }
    
    /**
     * Find task by ID.
     *
     * @param id Task ID
     * @return Optional containing task if found
     */
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }
    
    /**
     * Save a task.
     *
     * @param task Task to save
     * @return Saved task
     */
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }
    
    /**
     * Delete a task.
     *
     * @param task Task to delete
     */
    public void delete(Task task) {
        tasks.remove(task.getId());
    }
}
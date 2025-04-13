package uk.gov.hmcts.reform.dev.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Task entity for caseworker task management.
 */
@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Status is required")
    private String status;
    
    @NotNull(message = "Due date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;
    
    /**
     * Default constructor.
     */
    public Task() {
    }
    
    /**
     * Constructor with required task details.
     *
     * @param title Task title
     * @param description Task description
     * @param status Current status of the task
     * @param dueDate Due date for task completion
     */
    public Task(String title, String description, String status, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }
    
    /**
     * Get task ID.
     *
     * @return Task ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set task ID.
     *
     * @param id Task ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get task title.
     *
     * @return Task title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Set task title.
     *
     * @param title Task title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get task description.
     *
     * @return Task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set task description.
     *
     * @param description Task description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get task status.
     *
     * @return Task status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Set task status.
     *
     * @param status Task status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Get task due date.
     *
     * @return Task due date
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    /**
     * Set task due date.
     *
     * @param dueDate Task due date
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
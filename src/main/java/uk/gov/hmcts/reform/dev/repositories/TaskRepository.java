package uk.gov.hmcts.reform.dev.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.Task;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TaskRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Task> findAll() {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t", Task.class);
        return query.getResultList();
    }
    
    public Optional<Task> findById(Long id) {
        Task task = entityManager.find(Task.class, id);
        return Optional.ofNullable(task);
    }
    
    public Task save(Task task) {
        if (task.getId() == null) {
            entityManager.persist(task);
            return task;
        } else {
            return entityManager.merge(task);
        }
    }
    
    public void delete(Task task) {
        if (entityManager.contains(task)) {
            entityManager.remove(task);
        } else {
            entityManager.remove(entityManager.merge(task));
        }
    }
}
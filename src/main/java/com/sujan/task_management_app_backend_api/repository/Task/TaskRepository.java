package com.sujan.task_management_app_backend_api.repository.Task;

import com.sujan.task_management_app_backend_api.model.Task.Task;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // User's tasks - paginated
    Page<Task> findByOwnerUsername(String username, Pageable pageable);

    // User's tasks filtered by status
    Page<Task> findByOwnerUsernameAndStatus(String username, TaskStatus status, Pageable pageable);

    // Single task with owner check
    Optional<Task> findByIdAndOwnerUsername(Long id, String username);

    // Admin: all tasks by status
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
}

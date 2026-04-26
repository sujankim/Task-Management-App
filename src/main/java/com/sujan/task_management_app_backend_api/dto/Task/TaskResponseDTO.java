package com.sujan.task_management_app_backend_api.dto.Task;

import com.sujan.task_management_app_backend_api.model.Task.TaskPriority;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDate dueDate,
        LocalDateTime createdAt,
        String ownerUsername
) {
}

package com.sujan.task_management_app_backend_api.dto.Task;

import com.sujan.task_management_app_backend_api.model.Task.TaskPriority;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;

public record UpdateTaskRequestDTO(
        String title,

        String description,

        TaskStatus status,

        TaskPriority priority,

        @Future(message = "Due date must be in the future")
        LocalDate dueDate
) {
}

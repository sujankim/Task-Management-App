package com.sujan.task_management_app_backend_api.dto.Task;

import com.sujan.task_management_app_backend_api.model.Task.TaskPriority;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateTaskRequestDTO(

        @NotBlank(message = "Title is required")
        String title,

        String description,

        TaskStatus status,

        TaskPriority priority,

        @Future(message = "Due date must be in the future")
        LocalDate dueDate
) {
    public CreateTaskRequestDTO {
        if (status == null) {
            status = TaskStatus.TODO;
        }
        if (priority == null) {
            priority = TaskPriority.MEDIUM;
        }
    }
}

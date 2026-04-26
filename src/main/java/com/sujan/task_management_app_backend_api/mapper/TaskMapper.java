package com.sujan.task_management_app_backend_api.mapper;

import com.sujan.task_management_app_backend_api.dto.Task.CreateTaskRequestDTO;
import com.sujan.task_management_app_backend_api.dto.Task.TaskResponseDTO;
import com.sujan.task_management_app_backend_api.dto.Task.UpdateTaskRequestDTO;
import com.sujan.task_management_app_backend_api.model.Task.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toModel (CreateTaskRequestDTO createTaskRequestDTO) {
        Task task = new Task();
        task.setTitle(createTaskRequestDTO.title());
        task.setDescription(createTaskRequestDTO.description());
        task.setPriority(createTaskRequestDTO.priority());
        task.setStatus(createTaskRequestDTO.status());
        task.setDueDate(createTaskRequestDTO.dueDate());
        return task;
    }

    public void updateModel(Task task, UpdateTaskRequestDTO updateTaskRequestDTO) {
        if (updateTaskRequestDTO.title() != null) {
            task.setTitle(updateTaskRequestDTO.title());
        }
        if (updateTaskRequestDTO.description() != null) {
            task.setDescription(updateTaskRequestDTO.description());
        }
        if (updateTaskRequestDTO.status() != null) {
            task.setStatus(updateTaskRequestDTO.status());
        }
        if (updateTaskRequestDTO.priority() != null) {
            task.setPriority(updateTaskRequestDTO.priority());
        }
        if (updateTaskRequestDTO.dueDate() != null) {
            task.setDueDate(updateTaskRequestDTO.dueDate());
        }
    }

    public TaskResponseDTO toDTO (Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getOwner().getUsername()
        );
    }
}

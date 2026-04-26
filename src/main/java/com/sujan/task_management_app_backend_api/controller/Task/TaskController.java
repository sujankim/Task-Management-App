package com.sujan.task_management_app_backend_api.controller.Task;

import com.sujan.task_management_app_backend_api.dto.Task.CreateTaskRequestDTO;
import com.sujan.task_management_app_backend_api.dto.Task.TaskResponseDTO;
import com.sujan.task_management_app_backend_api.dto.Task.UpdateTaskRequestDTO;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;
import com.sujan.task_management_app_backend_api.service.Task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "Task CURD API operations")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(
            summary = "Create a new task",
            description = "Creates a task for the currently authenticated user")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskRequestDTO createTaskRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(createTaskRequestDTO));
    }

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(
            summary = "Get current user's tasks",
            description = "Retrieves all tasks belonging to the currently authenticated user with pagination support")
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getUserTasks(pageable));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(
            summary = "Get task by ID",
            description = "Retrieves a specific task by its ID. Only the task owner can access it.")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @GetMapping("/status/{status}")
    @Transactional(readOnly = true)
    @Operation(
            summary = "Filter tasks by status",
            description = "Retrieves current user's tasks filtered by the specified status")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByStatus(
            @PathVariable TaskStatus status, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getUserTasksByStatus(status, pageable));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a task",
            description = "Updates an existing task. Only the task owner can update it.")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequestDTO request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a task",
            description = "Deletes a task by its ID. Only the task owner can delete it.")
    public ResponseEntity<Void> deleteAnyTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}

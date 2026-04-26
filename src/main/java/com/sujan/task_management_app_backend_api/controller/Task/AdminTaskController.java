package com.sujan.task_management_app_backend_api.controller.Task;

import com.sujan.task_management_app_backend_api.dto.Task.TaskResponseDTO;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;
import com.sujan.task_management_app_backend_api.service.Task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/tasks")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Task Management", description = "Admin endpoints for managing all users' tasks")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminTaskController {

    private final TaskService taskService;

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(
            summary = "Get all tasks (Admin only)",
            description = "Retrieves all tasks from all users. Only accessible by administrators.")
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(
            Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(pageable));
    }

    @GetMapping("/status/{status}")
    @Transactional(readOnly = true)
    @Operation(
            summary = "Filter all tasks by status (Admin only)",
            description = "Retrieves all users' tasks filtered by status. Admin access only.")
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasksByStatus(
            @Parameter(description = "Status to filter by", example = "TODO")
            @PathVariable TaskStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasksByStatus(status, pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete any task (Admin only)",
            description = "Allows administrators to delete any user's task")
    public ResponseEntity<Void> deleteAnyTask(
            @Parameter(description = "Task ID to delete", example = "1")
            @PathVariable Long id) {
        taskService.adminDeleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

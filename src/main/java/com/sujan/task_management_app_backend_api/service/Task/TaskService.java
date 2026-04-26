package com.sujan.task_management_app_backend_api.service.Task;

import com.sujan.task_management_app_backend_api.dto.Task.CreateTaskRequestDTO;
import com.sujan.task_management_app_backend_api.dto.Task.TaskResponseDTO;
import com.sujan.task_management_app_backend_api.dto.Task.UpdateTaskRequestDTO;
import com.sujan.task_management_app_backend_api.exception.ResourceNotFoundException;
import com.sujan.task_management_app_backend_api.mapper.TaskMapper;
import com.sujan.task_management_app_backend_api.model.Task.Task;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;
import com.sujan.task_management_app_backend_api.model.User;
import com.sujan.task_management_app_backend_api.repository.Task.TaskRepository;
import com.sujan.task_management_app_backend_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    private User getCurrentUser(){
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    //  Create a new task for the current user
    public TaskResponseDTO createTask(CreateTaskRequestDTO createTaskRequestDTO) {
        User currentUser = getCurrentUser();

        Task  task = new Task();
        task.setOwner(currentUser);
        task.setTitle(createTaskRequestDTO.title());
        task.setDescription(createTaskRequestDTO.description());
        task.setStatus(createTaskRequestDTO.status());
        task.setPriority(createTaskRequestDTO.priority());
        task.setDueDate(createTaskRequestDTO.dueDate());

        Task savedTask =  taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    // Get all tasks for the current user
    public Page<TaskResponseDTO> getUserTasks(Pageable pageable) {
        User currentUser = getCurrentUser();
        return taskRepository.findByOwnerUsername(currentUser.getUsername(), pageable)
                .map(taskMapper::toDTO);
    }

    //  Get tasks filtered by status for the current user
    public Page<TaskResponseDTO> getUserTasksByStatus(TaskStatus status, Pageable pageable) {
        User currentUser = getCurrentUser();
        return taskRepository.findByOwnerUsernameAndStatus(
                        currentUser.getUsername(), status, pageable)
                .map(taskMapper::toDTO);
    }

    // Get a single task by ID (owner only)
    public TaskResponseDTO getTaskById(Long taskId) {
        User currentUser = getCurrentUser();
        Task task =  taskRepository.findByIdAndOwnerUsername(taskId,  currentUser.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found with id: " + taskId));
        return taskMapper.toDTO(task);
    }

    // Update a task (owner only)
    public TaskResponseDTO updateTask(Long taskId, UpdateTaskRequestDTO updateTaskRequestDTO) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findByIdAndOwnerUsername(taskId, currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        taskMapper.updateModel(task, updateTaskRequestDTO);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    // Delete a task (owner only)
    public void deleteTaskById(Long taskId) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findByIdAndOwnerUsername(taskId, currentUser.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found with id: " + taskId));
        taskRepository.delete(task);
    }

    // ─── ADMIN METHODS ──────────────────────────────────────────

    // Admin: Get all tasks from all users
    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toDTO);
    }

    // Admin: Get all tasks filtered by status
    public Page<TaskResponseDTO> getAllTasksByStatus(TaskStatus status, Pageable pageable) {
        return taskRepository.findByStatus(status, pageable)
                .map(taskMapper::toDTO);
    }


    // Admin: Delete any task
    public void adminDeleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId));
        taskRepository.delete(task);
    }
}

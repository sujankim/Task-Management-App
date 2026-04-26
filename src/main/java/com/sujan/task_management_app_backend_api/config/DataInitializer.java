package com.sujan.task_management_app_backend_api.config;

import com.sujan.task_management_app_backend_api.model.*;
import com.sujan.task_management_app_backend_api.model.Task.Task;
import com.sujan.task_management_app_backend_api.model.Task.TaskPriority;
import com.sujan.task_management_app_backend_api.model.Task.TaskStatus;
import com.sujan.task_management_app_backend_api.repository.Task.TaskRepository;
import com.sujan.task_management_app_backend_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev") // Only runs in dev profile
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Database already initialized. Skipping data seeding.");
            return;
        }

        log.info("Initializing sample data...");

        // Create users
        User regularUser = new User();
        regularUser.setUsername("user");
        regularUser.setPassword(passwordEncoder.encode("password123"));
        regularUser.setRole(Role.ROLE_USER);
        regularUser = userRepository.save(regularUser);

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRole(Role.ROLE_ADMIN);
        adminUser = userRepository.save(adminUser);

        // Create tasks for regular user
        List<Task> userTasks = List.of(
                createTask("Complete project documentation",
                        "Write comprehensive API documentation with Swagger examples",
                        TaskStatus.IN_PROGRESS, TaskPriority.HIGH,
                        LocalDate.now().plusDays(5), regularUser),
                createTask("Implement user authentication",
                        "Add JWT-based authentication with Spring Security",
                        TaskStatus.DONE, TaskPriority.HIGH,
                        LocalDate.now().plusDays(2), regularUser),
                createTask("Design database schema",
                        "Create ER diagram and define table relationships",
                        TaskStatus.DONE, TaskPriority.MEDIUM,
                        LocalDate.now().plusDays(3), regularUser),
                createTask("Write unit tests",
                        "Achieve 80% code coverage for service layer",
                        TaskStatus.TODO, TaskPriority.MEDIUM,
                        LocalDate.now().plusDays(10), regularUser),
                createTask("Setup CI/CD pipeline",
                        "Configure GitHub Actions for automated testing and deployment",
                        TaskStatus.TODO, TaskPriority.LOW,
                        LocalDate.now().plusDays(15), regularUser)
        );
        taskRepository.saveAll(userTasks);

        // Create tasks for admin user
        List<Task> adminTasks = List.of(
                createTask("Review system architecture",
                        "Review and approve the microservices architecture design",
                        TaskStatus.TODO, TaskPriority.HIGH,
                        LocalDate.now().plusDays(7), adminUser),
                createTask("Team performance review",
                        "Conduct quarterly performance reviews for the development team",
                        TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM,
                        LocalDate.now().plusDays(14), adminUser),
                createTask("Security audit",
                        "Perform security audit of the application",
                        TaskStatus.TODO, TaskPriority.HIGH,
                        LocalDate.now().plusDays(21), adminUser)
        );
        taskRepository.saveAll(adminTasks);

        log.info("Sample data initialized successfully!");
        log.info("Regular user: username='user', password='password123'");
        log.info("Admin user: username='admin', password='admin123'");
    }

    private Task createTask(String title, String description, TaskStatus status,
                            TaskPriority priority, LocalDate dueDate, User owner) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setDueDate(dueDate);
        task.setOwner(owner);
        return task;
    }
}

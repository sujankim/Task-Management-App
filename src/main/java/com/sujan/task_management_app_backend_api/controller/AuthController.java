package com.sujan.task_management_app_backend_api.controller;

import com.sujan.task_management_app_backend_api.dto.AuthResponse;
import com.sujan.task_management_app_backend_api.dto.LoginRequest;
import com.sujan.task_management_app_backend_api.dto.RegisterRequest;
import com.sujan.task_management_app_backend_api.dto.UserInfoResponse;
import com.sujan.task_management_app_backend_api.model.User;
import com.sujan.task_management_app_backend_api.repository.UserRepository;
import com.sujan.task_management_app_backend_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login, and current user info endpoints")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate and get JWT token")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get current authenticated user info")
    public ResponseEntity<UserInfoResponse> getCurrentUser() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return ResponseEntity.ok(
                new UserInfoResponse(
                        user.getUsername(),
                        user.getRole().name()
                )
        );
    }
}
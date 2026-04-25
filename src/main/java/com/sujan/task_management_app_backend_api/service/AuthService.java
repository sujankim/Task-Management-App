package com.sujan.task_management_app_backend_api.service;


import com.sujan.task_management_app_backend_api.dto.AuthResponse;
import com.sujan.task_management_app_backend_api.dto.LoginRequest;
import com.sujan.task_management_app_backend_api.dto.RegisterRequest;
import com.sujan.task_management_app_backend_api.exception.DuplicateResourceException;
import com.sujan.task_management_app_backend_api.model.Role;
import com.sujan.task_management_app_backend_api.model.User;
import com.sujan.task_management_app_backend_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationProvider authenticationProvider;


    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password())); // Bcrypt
        user.setRole(Role.ROLE_USER);//default role

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        // AuthenticationManager verifies credentials
        // Throws BadCredentialsException if wrong — Spring handles the 401
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
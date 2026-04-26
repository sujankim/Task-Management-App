package com.sujan.task_management_app_backend_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI taskManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management Application API")
                        .description("""
                                Full-stack capstone project API for managing tasks with JWT authentication.
                                
                                ## Features
                                * User registration and authentication with JWT
                                * Task CRUD operations
                                * Role-based access control (USER/ADMIN)
                                * Pagination and filtering support
                                * Admin dashboard endpoints
                                
                                ## Authentication
                                To access protected endpoints:
                                1. Register or login using `/api/auth/register` or `/api/auth/login`
                                2. Copy the JWT token from the response
                                3. Click the **Authorize** button and paste the token with format: `Bearer <your-token>`
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sujan")
                                .email("wiry-fester-kiln@duck.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("Production Server - placeholder")
                ))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT token with 'Bearer ' prefix")
                        ));
    }
}

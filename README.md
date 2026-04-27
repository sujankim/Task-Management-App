# 🧠 Task Manager Backend (Spring Boot)

![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-green)
![JWT](https://img.shields.io/badge/JWT-Security-blue)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-orange)

## 📌 Overview
Robust REST API for task management with authentication and role-based authorization.

## 🚀 Features
- JWT Authentication
- Role-based Access Control
- Task CRUD (User + Admin)
- Exception Handling
- DTO Mapping

## 🏗️ Architecture

```mermaid
graph TD
A[Controller] --> B[Service Layer]
B --> C[Repository Layer]
C --> D[(Database)]

B --> E[Security Context]
B --> F[DTO Mapper]
```

## 🔐 Security
- User can access only own tasks
- Admin can access all tasks

## 📂 Structure
```
controller/
service/
repository/
model/
dto/
mapper/
security/
```

## ⚙️ Setup
```bash
./mvnw spring-boot:run
```

## 🛠️ Dev Config
- Hibernate Auto Update
- SQL Logging Enabled

## 📡 API Endpoints
```
GET /api/tasks
POST /api/tasks
PUT /api/tasks/{id}
DELETE /api/tasks/{id}

ADMIN:
GET /api/admin/tasks
DELETE /api/admin/tasks/{id}
```

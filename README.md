# 🧠 Task Manager API (Spring Boot)

## ✅ CURRENT STATUS: PRODUCTION-LEVEL BACKEND READY

✔ JWT Authentication + Role-based Authorization  
✔ Task CRUD (User + Admin)  
✔ Swagger OpenAPI Integrated  
✔ Pagination & Filtering  
✔ Data Seeding (dev profile)  
✔ Exception Handling  

---

## 🚀 FEATURES

### 🔐 Authentication
- JWT login/register
- Role-based access (USER / ADMIN)

### 📋 Task Module
- Create Task (owner-bound)
- Get user tasks (paginated)
- Filter by status
- Update task (owner-only)
- Delete task (owner + admin)

### 👑 Admin Features
- View ALL tasks
- Filter ALL tasks
- Delete ANY task

### 📄 API Documentation
- Swagger UI enabled

---

## 📡 API ENDPOINTS

### Auth
POST /api/auth/register  
POST /api/auth/login  
GET /api/auth/me  

### Tasks (USER)
GET /api/tasks  
GET /api/tasks/{id}  
GET /api/tasks/status/{status}  
POST /api/tasks  
PUT /api/tasks/{id}  
DELETE /api/tasks/{id}  

### Admin
GET /api/admin/tasks  
GET /api/admin/tasks/status/{status}  
DELETE /api/admin/tasks/{id}  

---

## 📘 Swagger

http://localhost:8080/swagger-ui.html

Use Bearer token:
Authorization: Bearer <token>

---

## 🧠 SECURITY FLOW

1. Login → JWT token
2. Send token in header
3. Filter validates token
4. SecurityContext set
5. Access controlled via roles + ownership

---

## 📊 PROGRESS

### ✅ Phase 1 — Setup
✔ Completed

### ✅ Phase 2 — Auth
✔ Completed

### ✅ Phase 3 — Task Module
✔ Entity with owner mapping
✔ DTO + Mapper
✔ Repository (user-scoped queries)
✔ Service (secure logic)
✔ Controller (Swagger documented)

### ⏳ Phase 4 — Frontend
NEXT

---

## 🛠️ RUN

./mvnw spring-boot:run

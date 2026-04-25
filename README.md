# 🧠 Task Manager API (Spring Boot)

## ✅ Current Status: AUTH SYSTEM COMPLETE

JWT Authentication with role-based authorization is fully implemented.

---

## 🔐 Implemented Features

- User Registration & Login
- JWT Token Generation & Validation
- Role-based Authorization (USER / ADMIN)
- Custom Security Filters
- Exception Handling (401, 403, 409, 500)
- Global Validation Handling
- H2 Database (dev)

---

## 🧠 Security Flow

1. User logs in → receives JWT
2. JWT sent in Authorization header
3. JwtAuthenticationFilter validates token
4. SecurityContext is populated
5. Role-based access enforced

---

## 📡 Auth Endpoints

POST /api/auth/register  
POST /api/auth/login  
GET /api/auth/me  

---

## 📊 Progress

### ✅ Phase 1 — Setup
✔ Project initialized  
✔ Package structure  

### ✅ Phase 2 — Auth System
✔ User entity + Role enum  
✔ JWT Service  
✔ AuthService  
✔ SecurityConfig  
✔ Filters  

### ⏳ Phase 3 — Task Module (NEXT)
- Task Entity
- Task CRUD
- Ownership mapping

---

## 🛠️ Run

./mvnw spring-boot:run

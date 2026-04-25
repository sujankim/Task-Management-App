# 🔐 Security Implementation (Completed)

## JWT
- Contains username (subject)
- Contains role (custom claim)
- Has expiration

## Filter Flow
- Extract token
- Validate token
- Load user
- Set SecurityContext

## Roles
- ROLE_USER
- ROLE_ADMIN

## Exception Handling
- 401 → AuthenticationEntryPoint
- 403 → AccessDeniedHandler

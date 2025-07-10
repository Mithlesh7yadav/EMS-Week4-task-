# 👩‍💼 Employee Management System (Spring Boot + JWT + Role-Based Auth)

This project is a **Spring Boot REST API** that manages employees and departments.  
It supports **JWT authentication**, **role-based authorization**, and allows only authorized users (ADMIN / MANAGER) to access certain endpoints.

---

## 🚀 Features

- 🧾 CRUD operations for Employee & Department
- 🔐 JWT Authentication (Login / Register / Secure endpoints)
- 👮‍♂️ Role-Based Access Control (ADMIN, MANAGER, EMPLOYEE)
- 🔄 Self-referencing manager (Employee reports to another Employee)
- 🗃️ Employee ↔ Department relationship (`@ManyToOne`)
- 🧪 Input validation using Jakarta Validation
- 🔒 Password encoding with BCrypt
- 🧑‍💻 Swagger UI for testing APIs
- 🛠️ Default ADMIN user is auto-created at startup

---

## 📦 Tech Stack

| Layer             | Tech                          |
|------------------|-------------------------------|
| Language         | Java 8                      |
| Framework        | Spring Boot 3.x               |
| Security         | Spring Security + JWT (jjwt)  |
| ORM              | Spring Data JPA               |
| DB               | PostgreSQL                    |
| Build Tool       | Maven                         |
| Docs             | Swagger (SpringDoc OpenAPI)   |
| Boilerplate-free | Lombok                        |

---

## 📁 Project Structure

```text
com.ems
├── entity
│   ├── Employee.java
│   ├── Department.java
│   └── Role.java
├── controller
│   ├── EmployeeController.java
│   ├── DepartmentController.java
│   └── AuthController.java
├── repository
│   ├── EmployeeRepository.java
│   └── DepartmentRepository.java
├── service
│   ├── EmployeeService.java
│   └── DepartmentService.java
├── security
│   ├── JwtUtil.java
│   ├── JwtAuthFilter.java
│   ├── SecurityConfig.java
│   └── UserDetailsServiceImpl.java
├── config
│   └── DataInitializer.java  ← Creates default ADMIN user
└── exception
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java


-----------------------------------------------------------------------------------------------------
🧪 Default Admin Credentials
On first run, a default ADMIN user is created automatically:

makefile
Copy
Edit
Username: admin  
Password: admin123

------------------------------------------------------------------------------------------------------

🔐 Role Access Permissions
Role	Endpoints Allowed
ADMIN	All (/employees/**, /departments/**, promote)
MANAGER	View/manage team
EMPLOYEE	Basic profile access (future scope)

------------------------------------------------------------------------------------------------------

🔑 Authentication Endpoints
Endpoint	Method	Description
/register	POST	Register a new EMPLOYEE
/login	POST	Generate JWT token
/promote	POST	Promote user (ADMIN only)

------------------------------------------------------------------------------------------------------

💬 Sample JSON Inputs
➕ Register
POST /register
{
  "username": "john",
  "password": "john123",
  "age": 28,
  "salary": 50000,
  "project": "CRM"
}

------------------------------------------------------------------------------------------------------
🔐 Login

POST /login
{
  "username": "admin",
  "password": "admin123"
}
------------------------------------------------------------------------------------------------------

Returns a JWT like:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
------------------------------------------------------------------------------------------------------
⚙️ application.properties


spring.datasource.url=jdbc:postgresql://localhost:5432/ems_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

jwt.secret=YOUR_BASE64_SECRET_KEY
------------------------------------------------------------------------------------------------------

📬 API Endpoints Summary
🔐 Auth
POST /register

POST /login

POST /promote (Admin only)

👨‍💼 Employees
GET /employees

GET /employees/{id}

POST /employees (Admin only)

PUT /employees/{id}

DELETE /employees/{id}

🏢 Departments
GET /departments

POST /departments

PUT /departments/{id}

DELETE /departments/{id}

✅ Future Enhancements
Add manager-only dashboards

Add employee self-profile updates

Upload documents (S3 / local)

Email notification integration

🙏 Acknowledgments
Spring Security Team

jjwt (Java JWT Library)

SpringDoc Swagger

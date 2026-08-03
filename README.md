# 💰 Expense Tracker API

A secure, RESTful Expense Tracker application built with Spring Boot. Users can register, log in using JWT authentication, and securely manage their personal expenses.

---

## 🚀 Features

- User Registration & Login
- JWT Authentication
- BCrypt Password Encryption
- Spring Security
- CRUD Operations for Expenses
- User-specific Expense Management
- Pagination & Sorting
- Global Exception Handling
- Request Validation
- Swagger API Documentation
- DTO Mapping using MapStruct
- SLF4J Logging

---

## 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JJWT)
- MapStruct
- Swagger / OpenAPI
- Maven

---

## 📂 Project Structure

```
src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── exception
 ├── mapper
 ├── repository
 ├── security
 └── service
```

---

## 🔐 Authentication Flow

1. Register a new user
2. Login with username & password
3. Receive JWT Token
4. Authorize using Swagger or Postman
5. Access protected APIs

---

## 📌 API Endpoints

### Authentication

| Method | Endpoint |
|---------|----------|
| POST | /auth/register |
| POST | /auth/login |

### Expenses

| Method | Endpoint |
|---------|----------|
| GET | /expenses |
| GET | /expenses/{id} |
| POST | /expenses |
| PUT | /expenses |
| DELETE | /expenses/{id} |

---

## 📖 Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

## ⚙️ Run Locally

Clone the project

```bash
git clone https://github.com/archanahuman/expense-tracker-api.git
```

Go to project folder

```bash
cd expense-tracker-api
```

Configure MySQL

```
application.properties
```

Run

```bash
mvn spring-boot:run
```

---

## 🔮 Future Enhancements

- React Frontend
- Dashboard Charts
- Monthly Reports
- Export to PDF/Excel
- Email Notifications
- Docker Deployment
- Cloud Deployment

---

## 👩‍💻 Author

**Archana Chelmila**

GitHub:
https://github.com/archanahuman

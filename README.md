# corporate-task-api
Corporate Task and Team Management REST API built with Java 17 and Spring Boot

# Corporate Task and Team Management API

RESTful API built with Java 17 and Spring Boot 3 for managing corporate team members and tracking task lifecycles. Designed using a layered architecture (Controller, Service, Repository, DTO) with relational persistence and automated unit testing.

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.4 (Spring Web, Spring Data JPA, Bean Validation)
- **ORM and Database:** Hibernate, H2 Relational Database (In-Memory)
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito
- **API Documentation:** OpenAPI 3 / Swagger UI

## Architecture and Key Features

- **Layered Design:** Strict separation of concerns across `controller`, `service`, `repository`, `model`, `dto`, and `exception` packages.
- **Relational Data Model:** One-to-Many relationship between `User` and `Task` entities using JPA annotations and custom JPQL queries.
- **Global Exception Handling:** Centralized error responses (`400 Bad Request`, `404 Not Found`) implemented with `@RestControllerAdvice`.
- **Unit Testing:** Service-layer unit tests verifying task creation, user assignment, and exception handling with Mockito.

## Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.8+

### Run the Application
```bash
mvn spring-boot:run
```

### Run Unit Tests
```bash
mvn test
```

### API Documentation
Once the application is running, access the interactive OpenAPI documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`

## Main Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/users` | Retrieve all team members |
| `POST` | `/api/v1/users` | Register a new team member |
| `GET` | `/api/v1/tasks` | Retrieve all tasks (optional `?status=` filter) |
| `POST` | `/api/v1/tasks` | Create and assign a new task |
| `PATCH` | `/api/v1/tasks/{id}/status` | Update task status (`PENDING`, `IN_PROGRESS`, `COMPLETED`) |
| `DELETE` | `/api/v1/tasks/{id}` | Remove a task by ID |

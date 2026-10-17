# E-Commerce REST API (sb-ecom)

### Enterprise-grade, clean-architecture Spring Boot backend for e-commerce platforms

> Robust APIs. DTO encapsulation. Centralized validation and exception handling.

![Architecture and API Flow](assets/architecture.png)

---

## Table of Contents

- [Problem Statement](#problem-statement)
- [Objective](#objective)
- [Solution Overview](#solution-overview)
- [Key Features](#key-features)
- [API Endpoints](#api-endpoints)
- [Technical Architecture and Stack](#technical-architecture-and-stack)
- [Setup and Run](#setup-and-run)
- [Testing the Application](#testing-the-application)

---

## Problem Statement

Modern e-commerce platforms demand backends that are resilient, modular, and maintain strict data integrity. When building retail systems, developers often run into recurring challenges:

- Direct exposure of persistent JPA entities to client APIs, causing tight coupling and potential security vulnerabilities.
- Inconsistent and uninformative validation error responses across different controllers.
- Hard-to-maintain business logic tangled directly within controller methods or data layers.
- Inefficient database querying and lack of standardized response envelopes for listing operations.

Without a well-defined layered architecture, scaling the catalog, product management, and order workflows quickly leads to technical debt and fragile APIs.

---

## Objective

Build a maintainable, high-performance, and modular RESTful backend foundation for e-commerce systems:

1. **Layered Separation of Concerns:** Decouple web controllers, service implementations, and JPA persistence tiers.
2. **DTO & Entity Decoupling:** Shield internal entity structures from external clients using ModelMapper and specialized DTOs (`CategoryDTO`, `CategoryResponse`).
3. **Fail-Fast Input Validation:** Enforce Bean Validation constraints at the API boundary before execution touches persistence logic.
4. **Centralized Error Handling:** Return clean, structured HTTP error payloads using `@RestControllerAdvice` for standard validations, missing entities, and business rule conflicts.
5. **Developer Ergonomics:** Streamline local development with embedded in-memory H2 database support and Spring Boot Actuator monitoring.

---

## Solution Overview

**sb-ecom** is a Spring Boot application designed following enterprise domain patterns:

- **Presentation Layer (`controller`):** Exposes public and administrative REST endpoints adhering to standard HTTP semantics.
- **Service Layer (`service`):** Contains business logic, duplicate checks, transaction management, and DTO conversion logic.
- **Persistence Layer (`repositories` & `model`):** Utilizes Spring Data JPA with Hibernate ORM to manage database records with optimized queries.
- **Exception Interceptor (`exceptions`):** Global exception handling translating Java exceptions (`MethodArgumentNotValidException`, `ResourceNotFoundException`, `APIException`) into consistent JSON responses.

---

## Key Features

| Feature | What it does |
|---|---|
| **Category Management** | Full CRUD capabilities for catalog categories with automated unique checks |
| **DTO Data Transformation** | Automatic mapping between domain entities and API payloads via ModelMapper |
| **Input Validation** | Hibernate Validator constraints (`@NotBlank`, `@Size`) ensuring catalog data hygiene |
| **Unified Error Handling** | Global exception advice returning field-specific validation errors and custom error statuses |
| **In-Memory H2 Console** | Web-based database management console for rapid development and testing |
| **Production Health Checks** | Built-in Spring Boot Actuator health and metrics endpoints |

---

## API Endpoints

### Category Management (`/api`)

| Method | Endpoint | Access | Description |
|:---:|:---|:---:|:---|
| `GET` | `/api/public/categories` | Public | Fetch all categories wrapped in a standardized response |
| `POST` | `/api/public/categories` | Public | Create a new category with payload validation |
| `PUT` | `/api/public/categories/{categoryId}` | Public | Update existing category by ID |
| `DELETE` | `/api/admin/categories/{categoryId}` | Admin | Delete category by ID |

#### Example Request Payload (`POST /api/public/categories`)

```json
{
  "categoryName": "Electronics"
}
```

#### Example Validation Error Response (`400 Bad Request`)

```json
{
  "categoryName": "Category Name must contain atleast 5 character"
}
```

---

## Technical Architecture and Stack

### Architecture Overview

```
Client Requests (REST)
         │
         ▼
┌──────────────────────────────────────────────┐
│        Spring Boot REST Controllers          │  <-- CategoryController
└──────────────────────┬───────────────────────┘
                       │ (DTOs)
                       ▼
┌──────────────────────────────────────────────┐
│           Service Layer & Mappers            │  <-- CategoryServiceImpl + ModelMapper
└──────────────────────┬───────────────────────┘
                       │ (Domain Entities)
                       ▼
┌──────────────────────────────────────────────┐
│          Spring Data JPA Repositories        │  <-- CategoryRepository
└──────────────────────┬───────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────┐
│            H2 / Relational Database          │  <-- In-Memory / Production DB
└──────────────────────────────────────────────┘
```

### Technology Stack

- **Framework:** Java 17, Spring Boot 3.4 / 4.x
- **Web MVC:** Spring Web MVC (`spring-boot-starter-webmvc`)
- **Persistence & ORM:** Spring Data JPA, Hibernate ORM
- **Database:** H2 Database Engine (In-Memory runtime)
- **Object Mapping:** ModelMapper (3.1.1)
- **Validation:** Hibernate Validator 9.x / Jakarta Validation
- **Boilerplate Reduction:** Project Lombok
- **Monitoring & Metrics:** Spring Boot Actuator

---

## Setup and Run

### Prerequisites

- Java Development Kit (JDK) 17+
- Apache Maven 3.8+ (or Maven Wrapper)

### 1. Clone & Navigate to Project

```bash
git clone <repository-url>
cd sb-ecom
```

### 2. Configure Environment

Review `src/main/resources/application.properties`:

```properties
spring.application.name=sb-ecom
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:test
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. Build & Run the Application

Using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

The service will start on port `8080`.

---

## Testing the Application

### 1. Health & Actuator Check

- Application Health: `http://localhost:8080/actuator/health`

### 2. H2 Database Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:test`
- Username: `sa`
- Password: *(leave blank)*

### 3. Quick cURL Commands

**Create a category:**
```bash
curl -X POST http://localhost:8080/api/public/categories \
  -H "Content-Type: application/json" \
  -d '{"categoryName": "Laptops"}'
```

**Get all categories:**
```bash
curl -X GET http://localhost:8080/api/public/categories
```

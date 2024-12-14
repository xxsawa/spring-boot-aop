# Spring Boot Security Middleware with AOP

This project demonstrates a Spring Boot application with custom security middleware that utilizes locally stored data to reduce latency. The application compares standard JWT authentication and Role-Based Access Control (RBAC) authorization with an Aspect-Oriented Programming (AOP) approach. The project highlights the benefits of performance and modularity using both Object-Oriented Programming (OOP) and AOP design patterns.

## Features

- **JWT Authentication**: Standard token-based authentication for secure API access.
- **RBAC Authorization**: Role-based access control to manage user permissions.
- **AOP Security Aspects**: Using Aspect-Oriented Programming (AOP) to modularize security concerns, making the system more scalable and easier to maintain.
- **Custom Security Middleware**: A custom authentication filter and entry point to manage JWT-based authentication and authorization in a modular way.
- **Locally Stored Data**: Local storage of user roles and permissions to reduce latency in authorization checks.
- **Performance Optimization**: Focus on performance improvements using AOP and caching strategies to minimize latency.

## Tech Stack

- **Spring Boot**: Java-based framework to create stand-alone, production-grade Spring-based applications.
- **JWT (JSON Web Token)**: Token-based authentication for stateless API security.
- **RBAC (Role-Based Access Control)**: Authorization model based on roles assigned to users.
- **Aspect-Oriented Programming (AOP)**: A programming paradigm that modularizes cross-cutting concerns such as security, logging, and transaction management.
- **Java 8+**: The application uses modern Java features such as lambdas, streams, and functional interfaces.

## Project Structure

The project is organized into the following key components:

- **Annotations**: Custom annotations such as `@RequireRole` to define role-based access control on methods.
- **Aspects**: Security-related aspects such as authentication and authorization are defined using AOP principles (`AuthenticationAspect`, `RegionAspect`, etc.).
- **Controllers**: API controllers that handle requests and delegate to services for business logic processing.
- **Services**: Service layer to handle business logic and interact with the database.
- **Models**: Entities representing the core business data (e.g., `User`, `Role`, `Guest`).
- **Security**: Custom classes for handling JWT authentication and authorization (`JwtAuthenticationFilter`, `JwtUtil`, etc.).

## Setup and Installation

### Prerequisites

- Java 8 or later
- Maven 3.x or later

### Steps to Run Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-boot-security-aop.git

# Jarvis Backend

A Spring Boot budget planning application with PostgreSQL integration.

## Tech Stack

![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?style=flat-square&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=flat-square&logo=apache-maven)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=flat-square&logo=docker)

## Quick Start

### Prerequisites
- Java 25
- Docker Desktop
- Maven (or use included wrapper)

### Setup & Run

1. **Start Database**
   ```bash
   docker-compose up -d
   ```

2. **Run Application**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Build Project**
   ```bash
   ./mvnw clean compile
   ```

## Features

- Budget planning and tracking
- Financial account management
- Transaction categorization
- RESTful API endpoints
- PostgreSQL data persistence

## Architecture

- **Layered Architecture**: Controller → Service → Repository → Entity
- **ORM**: Spring Data JPA
- **Database**: PostgreSQL with Docker Compose

## API Endpoints

The application provides REST endpoints for managing:
- Users (`/api/users`)
- Accounts (`/api/accounts`)
- Categories (`/api/categories`)
- Transactions (`/api/transactions`)

## Development

```bash
# Run tests
./mvnw test

# Package application
./mvnw clean package

# Fresh database start
docker-compose down -v && docker-compose up -d
```
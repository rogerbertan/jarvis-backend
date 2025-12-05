# Jarvis Backend

Budget planning backend with user-specific income and expense tracking.

## Tech Stack

![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen?style=flat-square&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=flat-square&logo=apache-maven)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=flat-square&logo=docker)
![Flyway](https://img.shields.io/badge/Flyway-11.14-red?style=flat-square)

## Quick Start

### Prerequisites
- Java 25
- Docker Desktop
- Maven (wrapper included)

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

- User account management
- Income and expense tracking
- Custom categories per user
- Flyway database migrations
- PostgreSQL persistence

## Architecture

- **Layered**: Controller → Service → Repository → Model
- **Entities**: User (UUID), Category, Income, Expense
- **Migrations**: Flyway-managed schema
- **Database**: PostgreSQL 16

## API Endpoints

- `/api/health` - Health check

## Development

```bash
# Run tests
./mvnw test

# Package application
./mvnw clean package

# Fresh database start
docker-compose down -v && docker-compose up -d
```
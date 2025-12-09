# Jarvis Backend - System Design

## High-Level Architecture

```mermaid
graph TB
    subgraph "Client Layer"
        CLIENT[HTTP Client/Frontend]
    end

    subgraph "API Layer - Controllers"
        HC[HealthController<br/>/api/health]
    end

    subgraph "Business Logic Layer - Services"
        US[UserService]
        CS[CategoryService]
        IS[IncomeService]
        ES[ExpenseService]
    end

    subgraph "Data Access Layer - Repositories"
        UR[UserRepository<br/>JpaRepository]
        CR[CategoryRepository<br/>JpaRepository]
        IR[IncomeRepository<br/>JpaRepository]
        ER[ExpenseRepository<br/>JpaRepository]
    end

    subgraph "Database Layer"
        DB[(PostgreSQL 16<br/>Database: postgresdb)]
    end

    subgraph "Exception Handling"
        GEH[GlobalExceptionHandler<br/>ControllerAdvice]
        JE[JarvisException<br/>Base Exception]
        CNF[ConflictException<br/>409]
        RNF[ResourceNotFoundException<br/>404]
        VE[ValidationException<br/>400]
    end

    CLIENT --> HC

    US --> UR
    CS --> CR
    CS --> UR
    IS --> IR
    IS --> UR
    IS --> CR
    ES --> ER
    ES --> UR
    ES --> CR

    UR --> DB
    CR --> DB
    IR --> DB
    ER --> DB

    HC -.-> GEH

    GEH --> JE
    JE --> CNF
    JE --> RNF
    JE --> VE

    style CLIENT fill:#e1f5ff,stroke:#01579b,stroke-width:2px,color:#000
    style DB fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style GEH fill:#ffcdd2,stroke:#c62828,stroke-width:2px,color:#000
```

## Component Architecture

```mermaid
graph LR
    subgraph "Spring Boot Application"
        direction TB

        subgraph "Web Layer"
            CONTROLLERS[Controllers<br/>REST Endpoints]
        end

        subgraph "Service Layer"
            SERVICES[Services<br/>Business Logic]
        end

        subgraph "Persistence Layer"
            REPOS[JPA Repositories]
            ENTITIES[Domain Entities]
        end

        subgraph "Cross-Cutting Concerns"
            EXC[Exception Handling]
            CONF[Configuration]
            DTO[DTOs]
        end
    end

    CONTROLLERS --> SERVICES
    SERVICES --> REPOS
    REPOS --> ENTITIES
    CONTROLLERS --> DTO
    SERVICES --> DTO
    CONTROLLERS -.-> EXC
    SERVICES --> CONF

    style CONTROLLERS fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style SERVICES fill:#c5cae9,stroke:#283593,stroke-width:2px,color:#000
    style REPOS fill:#d1c4e9,stroke:#4527a0,stroke-width:2px,color:#000
    style ENTITIES fill:#e1bee7,stroke:#6a1b9a,stroke-width:2px,color:#000
    style EXC fill:#ffcdd2,stroke:#c62828,stroke-width:2px,color:#000
    style CONF fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style DTO fill:#fff9c4,stroke:#f57f17,stroke-width:2px,color:#000
```

## Domain Model - Entity Relationships

```mermaid
erDiagram
    USER ||--o{ CATEGORY : "has many"
    USER ||--o{ INCOME : "has many"
    USER ||--o{ EXPENSE : "has many"
    CATEGORY ||--o{ INCOME : "categorizes"
    CATEGORY ||--o{ EXPENSE : "categorizes"

    USER {
        uuid id PK
        varchar email UK
        varchar full_name
        varchar avatar_url
        timestamp created_at
        timestamp updated_at
    }

    CATEGORY {
        integer id PK
        uuid user_id FK
        varchar name
        varchar type "INCOME or EXPENSE"
        varchar color
        timestamp created_at
    }

    INCOME {
        integer id PK
        uuid user_id FK
        integer category_id FK
        varchar title
        decimal amount "precision 10, scale 2"
        date date_incomed
        timestamp created_at
        timestamp updated_at
    }

    EXPENSE {
        integer id PK
        uuid user_id FK
        integer category_id FK
        varchar title
        decimal amount "precision 10, scale 2"
        date date_expensed
        timestamp created_at
        timestamp updated_at
    }
```

## API Endpoints Overview

```mermaid
graph TD
    subgraph "REST API - /api"
        direction TB

        subgraph "Health"
            H1[GET /health]
        end

        subgraph "Category Management - Future"
            C1[GET /categories]
            C2[POST /categories]
            C3[PUT /categories/:id]
            C4[DELETE /categories/:id]
        end

        subgraph "Income Management - Future"
            I1[GET /incomes]
            I2[GET /incomes/:id]
            I3[POST /incomes]
            I4[PUT /incomes/:id]
            I5[DELETE /incomes/:id]
        end

        subgraph "Expense Management - Future"
            E1[GET /expenses]
            E2[GET /expenses/:id]
            E3[POST /expenses]
            E4[PUT /expenses/:id]
            E5[DELETE /expenses/:id]
        end
    end

    style H1 fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style C1 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style C2 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style C3 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style C4 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style I1 fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style I2 fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style I3 fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style I4 fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style I5 fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style E1 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style E2 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style E3 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style E4 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style E5 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
```

## Request Flow - Create Income

```mermaid
sequenceDiagram
    participant Client
    participant IncomeController
    participant IncomeService
    participant CategoryRepository
    participant IncomeRepository
    participant Database
    participant GlobalExceptionHandler

    Client->>IncomeController: POST /api/incomes
    activate IncomeController

    IncomeController->>IncomeService: save(IncomeRequestDTO)
    activate IncomeService

    IncomeService->>CategoryRepository: findById(categoryId)
    CategoryRepository->>Database: SELECT FROM categories WHERE id=?
    Database-->>CategoryRepository: Category or empty
    CategoryRepository-->>IncomeService: Optional<Category>

    alt Category not found
        IncomeService->>GlobalExceptionHandler: throw ResourceNotFoundException
        GlobalExceptionHandler-->>Client: 404 - Category not found
    end

    alt Category type mismatch
        IncomeService->>GlobalExceptionHandler: throw ValidationException
        GlobalExceptionHandler-->>Client: 400 - Category must be INCOME type
    end

    IncomeService->>IncomeService: Create Income entity<br/>Set created_at, updated_at

    IncomeService->>IncomeRepository: save(entity)
    IncomeRepository->>Database: INSERT INTO incomes
    Database-->>IncomeRepository: Saved entity
    IncomeRepository-->>IncomeService: Income entity

    IncomeService->>IncomeService: toResponseDTO(entity)
    IncomeService-->>IncomeController: IncomeResponseDTO
    deactivate IncomeService

    IncomeController-->>Client: 200 OK - IncomeResponseDTO
    deactivate IncomeController
```

## Exception Handling Flow

```mermaid
flowchart TD
    START[Request Received] --> CONTROLLER[Controller Layer]
    CONTROLLER --> SERVICE[Service Layer]
    SERVICE --> REPO[Repository Layer]

    REPO --> CHECK{Exception?}

    CHECK -->|No Exception| SUCCESS[Return Data]
    SUCCESS --> RESPONSE[200 OK Response]

    CHECK -->|JarvisException| JARVIS[JarvisException]
    CHECK -->|Validation Error| VALID[MethodArgumentNotValidException]
    CHECK -->|Other| OTHER[Generic Exception]

    JARVIS --> HANDLER[GlobalExceptionHandler]
    VALID --> HANDLER
    OTHER --> HANDLER

    HANDLER --> JARVIS_TYPE{Exception Type?}

    JARVIS_TYPE -->|ConflictException| E409[409 Conflict]
    JARVIS_TYPE -->|ResourceNotFoundException| E404[404 Not Found]
    JARVIS_TYPE -->|ValidationException| E400A[400 Bad Request]

    VALID --> E400B[400 Bad Request]
    OTHER --> E500[500 Internal Server Error]

    E409 --> ERROR_DTO[ErrorResponseDTO]
    E404 --> ERROR_DTO
    E400A --> ERROR_DTO
    E400B --> ERROR_DTO
    E500 --> ERROR_DTO

    ERROR_DTO --> ERROR_RESPONSE[Error Response to Client]

    style START fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style RESPONSE fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style HANDLER fill:#ffcdd2,stroke:#c62828,stroke-width:2px,color:#000
    style ERROR_RESPONSE fill:#ffcdd2,stroke:#c62828,stroke-width:2px,color:#000
    style E409 fill:#ffab91,stroke:#d84315,stroke-width:2px,color:#000
    style E404 fill:#ffab91,stroke:#d84315,stroke-width:2px,color:#000
    style E400A fill:#ffab91,stroke:#d84315,stroke-width:2px,color:#000
    style E400B fill:#ffab91,stroke:#d84315,stroke-width:2px,color:#000
    style E500 fill:#ef5350,stroke:#b71c1c,stroke-width:2px,color:#fff
```

## Data Flow - Income/Expense Queries

```mermaid
flowchart LR
    START[Client Request<br/>GET /api/incomes] --> SERVICE[IncomeService]

    SERVICE --> QUERY[Query all incomes]
    QUERY --> DB[(Load Incomes)]
    DB --> MAP[Map to DTOs]
    MAP --> RESULT[Return<br/>Income List]

    style START fill:#e1f5ff,stroke:#01579b,stroke-width:2px,color:#000
    style RESULT fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style SERVICE fill:#fff9c4,stroke:#f57f17,stroke-width:2px,color:#000
```

## Application Startup Flow

```mermaid
sequenceDiagram
    participant Docker
    participant SpringBoot
    participant Flyway
    participant Database
    participant Services

    Docker->>Database: Start PostgreSQL Container
    activate Database

    Docker->>SpringBoot: Start Application Container
    activate SpringBoot

    SpringBoot->>Flyway: Run Database Migrations
    activate Flyway

    Flyway->>Database: V1__Create_users_table.sql
    Database-->>Flyway: Table created

    Flyway->>Database: V2__Create_categories_table.sql
    Database-->>Flyway: Table created

    Flyway->>Database: V3__Create_incomes_table.sql
    Database-->>Flyway: Table created

    Flyway->>Database: V4__Create_expenses_table.sql
    Database-->>Flyway: Table created
    deactivate Flyway

    SpringBoot->>Services: Initialize Services
    activate Services
    Services-->>SpringBoot: Ready
    deactivate Services

    SpringBoot-->>Docker: Application Ready<br/>Health Check Passed

    Note over SpringBoot: Accepting HTTP Requests<br/>Port 8080

    deactivate SpringBoot
    deactivate Database
```

## Deployment Architecture

```mermaid
graph TB
    subgraph "Docker Environment"
        direction TB

        subgraph "jarvis-backend Container - Future"
            APP[Spring Boot Application<br/>Port: 8080<br/>User: jarvis UID:1001]
            JVM[JVM Options<br/>-Xms512m -Xmx1024m<br/>MaxRAMPercentage=75%]
            HC_CHECK[Health Check<br/>curl localhost:8080/api/health<br/>Every 30s]

            APP --- JVM
            APP --- HC_CHECK
        end

        subgraph "PostgreSQL Container"
            PG[PostgreSQL 16<br/>Port: 5432<br/>Database: postgresdb]
            VOL[Volume: postgres_data<br/>Persistent Storage]

            PG --- VOL
        end

        APP -->|JDBC Connection| PG
    end

    subgraph "External Access"
        CLIENT[External Client]
    end

    CLIENT -->|HTTP :8080| APP

    style APP fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style PG fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style VOL fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style CLIENT fill:#e1f5ff,stroke:#01579b,stroke-width:2px,color:#000
    style HC_CHECK fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
```

## Technology Stack

```mermaid
mindmap
    root((Jarvis Backend))
        Core Technologies
            Java 25
            Spring Boot 4.0.0
            Maven 3.9
        Database
            PostgreSQL 16
            Spring Data JPA
            Hibernate ORM
            Flyway Migrations
        Infrastructure
            Docker
            Docker Compose
            Multi-stage Build
        Development Tools
            Lombok
            Spring DevTools
            Maven Wrapper
        Data Handling
            BigDecimal precision
            LocalDateTime
            DTOs
        Deployment
            Alpine Linux
            Eclipse Temurin JRE
            Health Checks
```

## Key Design Patterns

### 1. **Layered Architecture (MVC)**
- **Controller Layer**: REST endpoints, request handling
- **Service Layer**: Business logic, validation
- **Repository Layer**: Data access abstraction
- **Entity Layer**: Domain models

### 2. **DTO Pattern**
- Separation between API contracts and domain entities
- Request/Response DTOs for each resource
- Prevents over-fetching and security issues

### 3. **Repository Pattern**
- JPA repositories abstract database operations
- Custom query methods where needed
- Clean separation from business logic

### 4. **Exception Handling Strategy**
- Custom exception hierarchy extending base `JarvisException`
- Global exception handler with `@ControllerAdvice`
- Consistent error response format

### 5. **Dependency Injection**
- Spring's IoC container manages dependencies
- Constructor injection for services
- Loose coupling between components

### 6. **Transaction Management**
- Declarative transactions with `@Transactional`
- ACID compliance
- Read-only transactions for query operations

### 7. **Configuration Externalization**
- `@ConfigurationProperties` for structured config
- Environment variable overrides
- Sensible defaults

## Security Considerations

1. **Container Security**: Non-root user (jarvis, UID 1001)
2. **Database Isolation**: Service-to-service communication via Docker network
3. **Input Validation**: DTO validation annotations
4. **Exception Handling**: No sensitive data in error messages
5. **Monetary Precision**: BigDecimal prevents calculation errors

## Scalability Notes

1. **Stateless Services**: No session state in application
2. **Database Connection Pooling**: HikariCP default in Spring Boot
3. **Read-Only Transactions**: Optimized for query operations
4. **Containerization**: Easy horizontal scaling
5. **Health Checks**: Kubernetes/orchestration ready

## API Design Principles

1. **RESTful**: Standard HTTP methods (GET, POST, PUT, DELETE)
2. **Resource-Oriented**: Clear resource URLs (/api/transactions)
3. **Consistent Responses**: Standardized DTO formats
4. **Error Handling**: Meaningful HTTP status codes
5. **Filtering Support**: Query parameters for complex searches
6. **KISS Principle**: Simple, maintainable design

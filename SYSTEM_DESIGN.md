# Jarvis Backend - System Design

## High-Level Architecture

```mermaid
graph TB
    subgraph "Client Layer"
        CLIENT[HTTP Client/Frontend]
    end

    subgraph "API Layer - Controllers"
        HC[HealthController<br/>/api/health]
        AC[AccountController<br/>/api/account]
        CC[CategoryController<br/>/api/categories]
        TC[TransactionController<br/>/api/transactions]
    end

    subgraph "Business Logic Layer - Services"
        AS[AccountService]
        CS[CategoryService]
        TS[TransactionService]
    end

    subgraph "Data Access Layer - Repositories"
        AR[AccountRepository<br/>JpaRepository]
        CR[CategoryRepository<br/>JpaRepository]
        TR[TransactionsRepository<br/>JpaRepository]
    end

    subgraph "Database Layer"
        DB[(PostgreSQL 16<br/>Database: jarvisdb)]
    end

    subgraph "Configuration & Initialization"
        CONF[AppConfig<br/>Default Account: 1]
        INIT[DataInitializer<br/>Seed Default Account]
    end

    subgraph "Exception Handling"
        GEH[GlobalExceptionHandler<br/>ControllerAdvice]
        JE[JarvisException<br/>Base Exception]
        CNF[ConflictException<br/>409]
        RNF[ResourceNotFoundException<br/>404]
        VE[ValidationException<br/>400]
    end

    CLIENT --> HC
    CLIENT --> AC
    CLIENT --> CC
    CLIENT --> TC

    AC --> AS
    CC --> CS
    TC --> TS

    AS --> AR
    AS --> CONF
    CS --> CR
    TS --> TR
    TS --> AR
    TS --> CR

    AR --> DB
    CR --> DB
    TR --> DB

    INIT --> AR
    CONF -.-> AS

    AC -.-> GEH
    CC -.-> GEH
    TC -.-> GEH

    GEH --> JE
    JE --> CNF
    JE --> RNF
    JE --> VE

    style CLIENT fill:#e1f5ff,stroke:#01579b,stroke-width:2px,color:#000
    style DB fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style GEH fill:#ffcdd2,stroke:#c62828,stroke-width:2px,color:#000
    style CONF fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style INIT fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
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
    ACCOUNT ||--o{ TRANSACTIONS : "has many"
    CATEGORY ||--o{ TRANSACTIONS : "categorizes"

    ACCOUNT {
        bigint id PK
        varchar name
        decimal initial_balance
        decimal current_balance
    }

    CATEGORY {
        bigint id PK
        varchar name UK
        varchar type
    }

    TRANSACTIONS {
        bigint id PK
        bigint account_id FK
        bigint category_id FK
        decimal amount
        varchar description
        timestamp date
        timestamp created_at
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

        subgraph "Account Management"
            A1[GET /account/balance]
            A2[PUT /account/balance]
        end

        subgraph "Category Management"
            C1[GET /categories]
            C2[POST /categories]
            C3[PUT /categories/:id]
            C4[DELETE /categories/:id]
        end

        subgraph "Transaction Management"
            T1[GET /transactions]
            T2[GET /transactions/:id]
            T3[POST /transactions]
            T4[PUT /transactions/:id]
            T5[DELETE /transactions/:id]
        end
    end

    style H1 fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style A1 fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style A2 fill:#bbdefb,stroke:#1565c0,stroke-width:2px,color:#000
    style C1 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style C2 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style C3 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style C4 fill:#ffecb3,stroke:#f57f17,stroke-width:2px,color:#000
    style T1 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style T2 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style T3 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style T4 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
    style T5 fill:#f8bbd0,stroke:#c2185b,stroke-width:2px,color:#000
```

## Request Flow - Create Transaction

```mermaid
sequenceDiagram
    participant Client
    participant TransactionController
    participant TransactionService
    participant AccountRepository
    participant CategoryRepository
    participant TransactionsRepository
    participant Database
    participant GlobalExceptionHandler

    Client->>TransactionController: POST /api/transactions
    activate TransactionController

    TransactionController->>TransactionService: save(TransactionRequestDTO)
    activate TransactionService

    TransactionService->>AccountRepository: existsById(accountId)
    AccountRepository->>Database: SELECT EXISTS(...)
    Database-->>AccountRepository: true/false
    AccountRepository-->>TransactionService: Account exists

    alt Account not found
        TransactionService->>GlobalExceptionHandler: throw ResourceNotFoundException
        GlobalExceptionHandler-->>Client: 404 - Account not found
    end

    TransactionService->>CategoryRepository: existsById(categoryId)
    CategoryRepository->>Database: SELECT EXISTS(...)
    Database-->>CategoryRepository: true/false
    CategoryRepository-->>TransactionService: Category exists

    alt Category not found
        TransactionService->>GlobalExceptionHandler: throw ResourceNotFoundException
        GlobalExceptionHandler-->>Client: 404 - Category not found
    end

    TransactionService->>TransactionService: Create Transactions entity<br/>Set created_at timestamp

    TransactionService->>TransactionsRepository: save(entity)
    TransactionsRepository->>Database: INSERT INTO transactions
    Database-->>TransactionsRepository: Saved entity
    TransactionsRepository-->>TransactionService: Transaction entity

    TransactionService->>TransactionService: toResponseDTO(entity)
    TransactionService-->>TransactionController: TransactionResponseDTO
    deactivate TransactionService

    TransactionController-->>Client: 200 OK - TransactionResponseDTO
    deactivate TransactionController
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

## Data Flow - Transaction Filtering

```mermaid
flowchart LR
    START[Client Request<br/>GET /api/transactions] --> PARAMS{Filter<br/>Parameters?}

    PARAMS -->|No Filters| SIMPLE[findAll]
    PARAMS -->|With Filters| FILTER[findAllWithFilters]

    SIMPLE --> DB1[(Load All<br/>Transactions)]
    FILTER --> DB2[(Load All<br/>Transactions)]

    DB1 --> MAP1[Map to DTOs]
    DB2 --> STREAM[Stream Processing]

    STREAM --> F1{accountId<br/>filter?}
    F1 -->|Yes| FILTER_ACC[Filter by<br/>account_id]
    F1 -->|No| F2
    FILTER_ACC --> F2

    F2{categoryId<br/>filter?}
    F2 -->|Yes| FILTER_CAT[Filter by<br/>category_id]
    F2 -->|No| F3
    FILTER_CAT --> F3

    F3{Date range<br/>filter?}
    F3 -->|Yes| FILTER_DATE[Filter by<br/>startDate & endDate]
    F3 -->|No| F4
    FILTER_DATE --> F4

    F4{Amount range<br/>filter?}
    F4 -->|Yes| FILTER_AMT[Filter by<br/>minAmount & maxAmount]
    F4 -->|No| MAP2
    FILTER_AMT --> MAP2

    MAP2[Map to DTOs]
    MAP1 --> RESULT[Return<br/>Transaction List]
    MAP2 --> RESULT

    style START fill:#e1f5ff,stroke:#01579b,stroke-width:2px,color:#000
    style RESULT fill:#c8e6c9,stroke:#2e7d32,stroke-width:2px,color:#000
    style STREAM fill:#fff9c4,stroke:#f57f17,stroke-width:2px,color:#000
```

## Application Startup Flow

```mermaid
sequenceDiagram
    participant Docker
    participant SpringBoot
    participant Flyway
    participant Database
    participant DataInitializer
    participant AppConfig
    participant Services

    Docker->>Database: Start PostgreSQL Container
    activate Database

    Docker->>SpringBoot: Start Application Container
    activate SpringBoot

    SpringBoot->>Flyway: Run Database Migrations
    activate Flyway

    Flyway->>Database: V1__Create_account_table.sql
    Database-->>Flyway: Table created

    Flyway->>Database: V2__Create_category_table.sql
    Database-->>Flyway: Table created

    Flyway->>Database: V3__Create_transactions_table.sql
    Database-->>Flyway: Table created
    deactivate Flyway

    SpringBoot->>AppConfig: Load Configuration<br/>jarvis.default.account.id=1
    activate AppConfig
    AppConfig-->>SpringBoot: Configuration loaded
    deactivate AppConfig

    SpringBoot->>DataInitializer: run() - ApplicationRunner
    activate DataInitializer

    DataInitializer->>Database: Check if accounts exist
    Database-->>DataInitializer: Empty table

    DataInitializer->>Database: INSERT default account<br/>name='Default Account'<br/>balances=0
    Database-->>DataInitializer: Account created (id=1)
    deactivate DataInitializer

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

        subgraph "jarvis-backend Container"
            APP[Spring Boot Application<br/>Port: 8080<br/>User: jarvis UID:1001]
            JVM[JVM Options<br/>-Xms512m -Xmx1024m<br/>MaxRAMPercentage=75%]
            HC_CHECK[Health Check<br/>curl localhost:8080/api/health<br/>Every 30s]

            APP --- JVM
            APP --- HC_CHECK
        end

        subgraph "jarvis-postgres Container"
            PG[PostgreSQL 16<br/>Port: 5432<br/>Database: jarvisdb]
            VOL[Volume: jarvis_data<br/>Persistent Storage]

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

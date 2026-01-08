<a id="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">Jarvis Backend</h3>

  <p align="center">
    Budget planning backend with user-specific income and expense tracking
    <br />
    <a href="https://github.com/rogerbertan/jarvis-backend/issues/new?labels=bug">Report Bug</a>
    ·
    <a href="https://github.com/rogerbertan/jarvis-backend/issues/new?labels=enhancement">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#architecture">Architecture</a></li>
    <li><a href="#features">Features</a></li>
    <li><a href="#development">Development</a></li>
    <li><a href="#contributing">Contributing</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Jarvis Backend is a modern budget planning application built with Spring Boot 4 and Java 25. It provides a robust REST API for managing user accounts, income tracking, expense tracking, and custom categorization.

Key capabilities:
* User account management with secure UUID-based identification
* Income and expense tracking with detailed categorization
* User-specific custom categories with type distinction (income/expense)
* Database schema versioning with Flyway migrations
* PostgreSQL persistence with optimized indexes for performance

The project follows a clean layered architecture (Controller → Service → Repository → Model) and implements the KISS principle (Keep It Simple, Stupid) for maintainability.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Java][Java-badge]][Java-url]
* [![Spring][Spring-badge]][Spring-url]
* [![PostgreSQL][PostgreSQL-badge]][PostgreSQL-url]
* [![Maven][Maven-badge]][Maven-url]
* [![Docker][Docker-badge]][Docker-url]
* [![Flyway][Flyway-badge]][Flyway-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

Ensure you have the following installed:
* Java 25
  ```sh
  java -version
  ```
* Docker Desktop
  ```sh
  docker --version
  ```
* Maven (wrapper included in project)

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/rogerbertan/jarvis-backend.git
   ```
2. Navigate to the project directory
   ```sh
   cd jarvis-backend
   ```
3. Start the PostgreSQL database
   ```sh
   docker-compose up -d
   ```
4. Run the application
   ```sh
   ./mvnw spring-boot:run
   ```
5. The application will start on `http://localhost:8080`

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

### Environment Variables

The application uses the following environment variables with defaults for development:

```properties
DB_HOST=localhost          # Database host
DB_NAME=postgresdb         # Database name
DB_USER=postgres           # Database user
DB_PASSWORD=postgres       # Database password
```

For production, update these values in your `application.properties` or set them as environment variables.

### API Endpoints

Currently available endpoints:

| Endpoint      | Method | Description                                         |
|---------------|--------|-----------------------------------------------------|
| `/api/health` | GET    | Health check endpoint - verifies application status |

**Example Response:**
```json
{
  "status": "UP"
}
```

_For more details on API endpoints, please refer to the [API Documentation](https://github.com/rogerbertan/jarvis-backend)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ARCHITECTURE -->
## Architecture

### Layered Architecture
The application follows a clean separation of concerns:
```
Controller → Service → Repository → Model (Entity)
```

### Domain Models

#### Entities
- **User**: User accounts with UUID primary key, email (unique), full name, avatar URL, timestamps
- **Category**: User-specific transaction categories with type (income/expense), color, and user reference
- **Income**: Income transactions with title, amount, category, date, and user reference
- **Expense**: Expense transactions with title, amount, category, date, and user reference
- **CategoryType**: Enum defining category types (INCOME, EXPENSE)

### Database Schema

- **Flyway Migrations**: Located in `src/main/resources/db/migration/`
  - V1-V3: Legacy tables (Account, Category, Transactions) - dropped in V4
  - V4: Drops all legacy tables
  - V5: Creates `users` table with UUID primary key
  - V6: Creates `categories` table with user foreign key
  - V7: Creates `incomes` table with user and category foreign keys
  - V8: Creates `expenses` table with user and category foreign keys
- **Indexes**: Optimized for user_id, category_id, and date queries
- **Constraints**: CASCADE delete for user-owned data, RESTRICT for category references

### Technology Stack
- **Java 25** - Latest Java version
- **Spring Boot 4.0.0** - Latest major version with Spring Data JPA
- **PostgreSQL 16** - Relational database
- **Flyway 11.14.1** - Database migration tool
- **Lombok** - Boilerplate reduction
- **Maven** - Dependency management
- **Docker** - Containerized PostgreSQL instance

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- FEATURES -->
## Features

- [x] User account management with UUID-based identification
- [x] Income and expense tracking per user
- [x] Custom categories per user with type distinction
- [x] Flyway database migrations for version control
- [x] PostgreSQL persistence with optimized indexes
- [x] RESTful API architecture
- [ ] User authentication and authorization
- [ ] Budget planning and forecasting
- [ ] Reporting and analytics
- [ ] Multi-currency support

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- DEVELOPMENT -->
## Development

### Build Commands

Compile the project:
```bash
./mvnw clean compile
```

Build package:
```bash
./mvnw clean package
```

### Testing

Run tests:
```bash
./mvnw test
```

### Database Management

Reset database (drops all data):
```bash
docker-compose down -v && docker-compose up -d
```

Stop database:
```bash
docker-compose down
```

### Development Notes

- Uses Spring Boot DevTools for hot reloading
- Flyway manages all database schema changes - **do not use JPA DDL auto-generation**
- Lombok annotations reduce boilerplate code (@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor)
- BigDecimal used for monetary values (DECIMAL(10,2) in database)
- UUID used for User IDs, Integer auto-increment for other entities
- Automatic timestamp management with @PrePersist and @PreUpdate
- Database setup: Use Docker container `local-postgres` with PostgreSQL 16
- All entities have created_at timestamps; Income/Expense have updated_at as well

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'feat: add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Commit Pattern

Follow [Conventional Commits](https://www.conventionalcommits.org/):
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `refactor:` - Code refactoring
- `test:` - Test additions/modifications
- `chore:` - Maintenance tasks

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/rogerbertan/jarvis-backend.svg?style=for-the-badge
[contributors-url]: https://github.com/rogerbertan/jarvis-backend/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/rogerbertan/jarvis-backend.svg?style=for-the-badge
[forks-url]: https://github.com/rogerbertan/jarvis-backend/network/members
[stars-shield]: https://img.shields.io/github/stars/rogerbertan/jarvis-backend.svg?style=for-the-badge
[stars-url]: https://github.com/rogerbertan/jarvis-backend/stargazers
[issues-shield]: https://img.shields.io/github/issues/rogerbertan/jarvis-backend.svg?style=for-the-badge
[issues-url]: https://github.com/rogerbertan/jarvis-backend/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/yourprofile

[Java-badge]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://openjdk.org/
[Spring-badge]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[PostgreSQL-badge]: https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/
[Maven-badge]: https://img.shields.io/badge/maven-%23C71A36.svg?style=for-the-badge&logo=apache-maven&logoColor=white
[Maven-url]: https://maven.apache.org/
[Docker-badge]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[Flyway-badge]: https://img.shields.io/badge/flyway-%23CC0200.svg?style=for-the-badge&logo=flyway&logoColor=white
[Flyway-url]: https://flywaydb.org/

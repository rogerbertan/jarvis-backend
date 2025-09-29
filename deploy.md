# Deployment Guide

## Local Development

### Quick Start
```bash
# Start PostgreSQL
docker-compose up -d

# Start application
./mvnw spring-boot:run
```

### Database Management
```bash
# Reset database (removes all data)
docker-compose down -v && docker-compose up -d

# Stop database
docker-compose down
```

## Docker Deployment

### Build and Run with Docker
```bash
# Build application image
docker build -t jarvis-backend .

# Run full stack with Docker
docker-compose up -d
docker run -p 8080:8080 --network jarvis-backend_default \
  -e DB_HOST=postgres -e DB_NAME=jarvisdb -e DB_USER=jarvis -e DB_PASSWORD=spring123 \
  jarvis-backend
```

### Management Commands
```bash
# Check service status
docker-compose ps

# View logs
docker-compose logs -f postgres

# Backup database
docker exec jarvis-postgres pg_dump -U jarvis jarvisdb > backup.sql

# Stop services
docker-compose down
```

## Configuration

### Database Settings
- **Host**: localhost (development) / postgres (Docker)
- **Database**: jarvisdb
- **User**: jarvis
- **Password**: spring123
- **Port**: 5432

### Application Settings
- Default values configured in `application.properties`
- Override with environment variables if needed:
  - `DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`
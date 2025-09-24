# Production Deployment Guide

## Quick Start

1. **Setup Environment Variables**
   ```bash
   cp .env.prod.template .env.prod
   # Edit .env.prod with your production values
   ```

2. **No directory setup needed**
   Docker named volumes will be created automatically

3. **Deploy Application**
   ```bash
   # Full deployment with all services
   docker-compose -f docker-compose.prod.yml --env-file .env.prod up -d

   # Or just the backend and database
   docker-compose -f docker-compose.prod.yml --env-file .env.prod up -d jarvis-backend postgres
   ```

## Production Features

### Security Enhancements
- ✅ Database not exposed to host network
- ✅ Non-root user in application container
- ✅ Environment-based secrets management
- ✅ Health checks for all services

### Performance Optimizations
- ✅ Multi-stage Docker builds for smaller images
- ✅ JVM tuning for container environments
- ✅ PostgreSQL performance configuration
- ✅ Nginx reverse proxy ready

### Monitoring & Reliability
- ✅ Health checks with automatic restart
- ✅ Persistent data volumes
- ✅ Application logging
- ✅ Database query monitoring

## Management Commands

```bash
# Check service status
docker-compose -f docker-compose.prod.yml ps

# View logs
docker-compose -f docker-compose.prod.yml logs -f jarvis-backend
docker-compose -f docker-compose.prod.yml logs -f postgres

# Scale the application (if needed)
docker-compose -f docker-compose.prod.yml up -d --scale jarvis-backend=2

# Update application
docker-compose -f docker-compose.prod.yml build jarvis-backend
docker-compose -f docker-compose.prod.yml up -d jarvis-backend

# Backup database
docker exec jarvis-postgres-prod pg_dump -U $POSTGRES_USER $POSTGRES_DB > backup.sql

# Stop services
docker-compose -f docker-compose.prod.yml down

# Stop and remove volumes (careful!)
docker-compose -f docker-compose.prod.yml down -v
```

## Configuration Notes

- **Database**: Configured for 100 connections with optimized memory settings
- **Application**: JVM heap set to 1GB max with G1 garbage collector
- **Nginx**: Optional reverse proxy for SSL termination and load balancing
- **Volumes**: Persistent data managed by Docker named volumes (system-wide)
#!/bin/bash

echo "Updating Jarvis Backend..."

git pull origin main

docker compose -f docker-compose.simple.yml build jarvis-backend

docker compose -f docker-compose.simple.yml up -d jarvis-backend

echo "Checking application health..."
sleep 10
curl -f http://localhost:8080/actuator/health || echo "Health check failed"

echo "Update complete!"
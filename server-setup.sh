#!/bin/bash
# Simple server deployment script

# 1. Setup environment
cp .env.prod.template .env.prod
echo "Edit .env.prod with your production passwords"

# 2. Make executable
chmod +x mvnw

# 3. Deploy
docker compose -f docker-compose.simple.yml --env-file .env.prod up -d

# 4. Check status
docker compose -f docker-compose.simple.yml ps
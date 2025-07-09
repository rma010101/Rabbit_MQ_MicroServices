# Microservices Docker Setup

This project contains two Spring Boot microservices that communicate via RabbitMQ:

- **Payment Service** (Port 8081)
- **Order Service** (Port 8082)
- **RabbitMQ** (Ports 5672, 15672)

## Prerequisites

- Docker Desktop installed and running
- Docker Compose

## Quick Start

### Option 1: Using the startup script (Windows)

```powershell
# Using PowerShell (recommended)
.\start-services.ps1

# Or using Command Prompt
start-services.bat
```

### Option 2: Manual Docker Compose commands

```bash
# Build and start all services
docker-compose up --build -d

# View status
docker-compose ps

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

## Service URLs

- **Payment Service**: http://localhost:8081
- **Order Service**: http://localhost:8082
- **RabbitMQ Management UI**: http://localhost:15672
  - Username: `guest`
  - Password: `guest`

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Order Service │    │    RabbitMQ     │    │ Payment Service │
│   (Port 8082)   │───▶│   (Port 5672)   │───▶│   (Port 8081)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Development

### Building individual services

```bash
# Build Payment Service
docker build -t payment-service .

# Build Order Service
docker build -t order-service ./orderservice
```

### Running services individually

```bash
# Start RabbitMQ first
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

# Start Payment Service
docker run -d --name payment-service -p 8081:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  -e SPRING_RABBITMQ_HOST=rabbitmq \
  --link rabbitmq:rabbitmq \
  payment-service

# Start Order Service
docker run -d --name order-service -p 8082:8080 \
  -e SPRING_PROFILES_ACTIVE=docker \
  -e SPRING_RABBITMQ_HOST=rabbitmq \
  --link rabbitmq:rabbitmq \
  order-service
```

## Monitoring

### View logs
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f payment-service
docker-compose logs -f order-service
docker-compose logs -f rabbitmq
```

### Check service health
```bash
# Container status
docker-compose ps

# Resource usage
docker stats

# Network inspection
docker network ls
docker network inspect paymentservice_microservices-network
```

## Troubleshooting

### Common Issues

1. **Port conflicts**: Make sure ports 8081, 8082, 5672, and 15672 are not in use
2. **Docker not running**: Ensure Docker Desktop is started
3. **Build failures**: Check that all Maven dependencies are available

### Useful Commands

```bash
# Clean rebuild
docker-compose down --volumes --remove-orphans
docker-compose build --no-cache
docker-compose up -d

# Remove all containers and images
docker system prune -a

# Access container shell
docker exec -it payment-service bash
docker exec -it order-service bash
```

## Configuration

Services use different configurations based on the active profile:

- **Local development**: `application.properties` (RabbitMQ on localhost)
- **Docker environment**: `application-docker.properties` (RabbitMQ on container network)

Environment variables can be modified in `docker-compose.yml`:

```yaml
environment:
  - SPRING_PROFILES_ACTIVE=docker
  - SPRING_RABBITMQ_HOST=rabbitmq
  - SPRING_RABBITMQ_PORT=5672
  - SPRING_RABBITMQ_USERNAME=guest
  - SPRING_RABBITMQ_PASSWORD=guest
```

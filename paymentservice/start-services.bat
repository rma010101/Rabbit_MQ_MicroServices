@echo off
echo ========================================
echo   Docker Setup for Microservices
echo ========================================

echo.
echo Building and starting all services...
echo.

REM Stop and remove existing containers if any
echo Stopping existing containers...
docker-compose down

echo.
echo Building Docker images...
docker-compose build

echo.
echo Starting services...
docker-compose up -d

echo.
echo ========================================
echo   Services Status
echo ========================================
docker-compose ps

echo.
echo ========================================
echo   Service URLs
echo ========================================
echo Payment Service: http://localhost:8081
echo Order Service:   http://localhost:8082
echo RabbitMQ UI:     http://localhost:15672 (guest/guest)
echo.
echo To view logs: docker-compose logs -f [service-name]
echo To stop:      docker-compose down
echo ========================================

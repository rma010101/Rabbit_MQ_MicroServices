Write-Host "========================================" -ForegroundColor Green
Write-Host "   Docker Setup for Microservices" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

Write-Host ""
Write-Host "Building and starting all services..." -ForegroundColor Yellow
Write-Host ""

# Stop and remove existing containers if any
Write-Host "Stopping existing containers..." -ForegroundColor Cyan
docker-compose down

Write-Host ""
Write-Host "Building Docker images..." -ForegroundColor Cyan
docker-compose build

Write-Host ""
Write-Host "Starting services..." -ForegroundColor Cyan
docker-compose up -d

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "   Services Status" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
docker-compose ps

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "   Service URLs" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host "Payment Service: http://localhost:8081" -ForegroundColor White
Write-Host "Order Service:   http://localhost:8082" -ForegroundColor White
Write-Host "RabbitMQ UI:     http://localhost:15672 (guest/guest)" -ForegroundColor White
Write-Host ""
Write-Host "To view logs: docker-compose logs -f [service-name]" -ForegroundColor Gray
Write-Host "To stop:      docker-compose down" -ForegroundColor Gray
Write-Host "========================================" -ForegroundColor Green

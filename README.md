# RabbitMQ Microservices Example

A Spring Boot microservices project demonstrating asynchronous communication using RabbitMQ message queue.

## 🏗️ Architecture

```
Client → OrderService → RabbitMQ → PaymentService
```

- **OrderService**: REST API that receives order requests and publishes messages to RabbitMQ
- **PaymentService**: Consumer service that processes payment for received orders
- **RabbitMQ**: Message broker handling asynchronous communication

## 🚀 Features

- **Single Order Processing**: Create individual orders via REST API
- **Batch Order Processing**: Submit multiple orders in a single request
- **Asynchronous Processing**: Orders are processed asynchronously through message queues
- **Dockerized**: Complete containerization with Docker Compose
- **Fault Tolerant**: Individual order failures don't affect other orders

## 📋 Prerequisites

- Java 17+
- Maven 3.6+
- Docker & Docker Compose
- Postman (for testing)

## 🛠️ Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/rabbitmq-microservices.git
cd rabbitmq-microservices
```

### 2. Build the Services
```bash
# Build OrderService
cd orderservice
./mvnw clean package -DskipTests
cd ..

# Build PaymentService
cd paymentservice
./mvnw clean package -DskipTests
cd ..
```

### 3. Start All Services

turn on Docker Desktop (on your laptop)
```bash
docker-compose up --build -d (type this string on the terminal)
```

### 4. Verify Services are Running
```bash
docker ps -a
```

You should see:
- `orderservice` on port 8080
- `paymentservice` on port 8081
- `rabbitmq` on ports 5672 (AMQP) and 15672 (Management UI)

## 🔗 API Endpoints

### Single Order
```http
POST http://localhost:8080/orders
Content-Type: application/json

{
  "orderId": "ORD-001",
  "item": "Gaming Laptop",
  "amount": 1299.99
}
```

### Batch Orders
```http
POST http://localhost:8080/orders/batch
Content-Type: application/json

[
  {
    "orderId": "BATCH-001",
    "item": "Gaming Mouse",
    "amount": 59.99
  },
  {
    "orderId": "BATCH-002",
    "item": "Mechanical Keyboard",
    "amount": 129.99
  }
]
```

## 📊 Monitoring

### RabbitMQ Management UI
- **URL**: http://localhost:15672
- **Username**: guest
- **Password**: guest

### Check Service Logs
```bash
# OrderService logs
docker logs orderservice

# PaymentService logs
docker logs paymentservice

# RabbitMQ logs
docker logs rabbitmq
```

## 🧪 Testing with Postman

### Test Data Examples

**E-commerce Bundle:**
```json
[
  {
    "orderId": "ELEC-001",
    "item": "4K Webcam",
    "amount": 149.99
  },
  {
    "orderId": "ELEC-002",
    "item": "USB-C Hub 7-in-1",
    "amount": 45.50
  }
]
```

**Office Supplies:**
```json
[
  {
    "orderId": "OFF-001",
    "item": "Ergonomic Office Chair",
    "amount": 299.00
  },
  {
    "orderId": "OFF-002",
    "item": "Standing Desk",
    "amount": 189.99
  }
]
```

## 🏃‍♂️ Development Workflow

### Making Code Changes

1. **Modify the code** in your IDE
2. **Rebuild the service**:
   ```bash
   cd orderservice  # or paymentservice
   ./mvnw clean package -DskipTests
   cd ..
   ```
3. **Restart the container**:
   ```bash
   docker-compose restart orderservice  # or paymentservice
   ```

### Full System Restart
```bash
docker-compose down
docker-compose up --build -d
```

## 📁 Project Structure

```
├── docker-compose.yml          # Multi-container orchestration
├── .gitignore                  # Git ignore rules
├── README.md                   # This file
├── orderservice/              # Order REST API service
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/java/com/rabbit_mq_example/orderservice/
│           ├── OrderserviceApplication.java
│           ├── OrderController.java    # REST endpoints
│           ├── Order.java             # Order model
│           └── RabbitMQConfig.java    # Queue configuration
└── paymentservice/            # Payment processing service
    ├── Dockerfile
    ├── pom.xml
    └── src/
        └── main/java/com/rabbitmqexample/paymentservice/
            ├── PaymentserviceApplication.java
            ├── OrderListener.java     # Message consumer
            └── Order.java            # Order model
```

## ⚙️ Configuration

### Application Properties
Both services use `application.properties` for configuration:

```properties
# OrderService (port 8080)
spring.application.name=orderservice
server.port=8080
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# PaymentService (port 8081)
spring.application.name=paymentservice
server.port=8081
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### Docker Environment Variables
Docker Compose overrides RabbitMQ host for container networking:
```yaml
environment:
  SPRING_RABBITMQ_HOST: rabbitmq
```

## 🔧 Troubleshooting

### Container Issues
```bash
# Check container status
docker ps -a

# View container logs
docker logs <container_name>

# Restart specific service
docker-compose restart <service_name>
```

### Connection Issues
- Ensure all containers are running
- Check if ports are available (8080, 8081, 5672, 15672)
- Verify RabbitMQ is accepting connections

### Build Issues
```bash
# Clean rebuild
docker-compose down
docker system prune -f
docker-compose up --build -d
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot for the microservices framework
- RabbitMQ for reliable message queuing
- Docker for containerization

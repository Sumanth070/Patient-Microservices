 Overview
This project is a production-grade microservices backend system designed to simulate real-world distributed architectures. It focuses on event-driven communication, scalability, resilience, and observability using AWS-native services.


## ⚙️ Tech Stack

### Backend

* Java 17+
* Spring Boot 3.X , 4.X
* Spring Security (JWT)
* Spring Data JPA

### Messaging

* AWS SNS (Event Publishing)
* AWS SQS (Queue Consumption)
* Dead Letter Queue (DLQ)

### Observability

* Spring Boot Actuator
* Prometheus
* Jaeger (Distributed Tracing)

### DevOps

* Docker
* GitHub Actions

---

## 🔁 Event Flow

1. Client creates appointment
2. Appointment Service publishes event → SNS
3. SNS distributes event → SQS queues
4. Notification Service consumes event
5. Retry mechanism applied on failure
6. Failed messages moved to DLQ

---

## 📡 API Documentation

> 📌 Swagger UI (add after deployment):

```
http://localhost:8080/swagger-ui.html
```

### 🔹 User Service

#### Register

```
POST /api/users/register
```

#### Login

```
POST /api/users/login
```

---

### 🔹 Appointment Service

#### Create Appointment

```
POST /api/appointments
```

---

## 📘 OpenAPI (Swagger)

Add this dependency:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

Access docs:

```
/swagger-ui.html
/v3/api-docs
```

---

## 🔥 Key Features

* Event-driven architecture (SNS + SQS)
* Retry + DLQ handling
* Idempotent event processing
* Distributed tracing (Jaeger)
* Metrics via Actuator + Prometheus

---

## 🧪 Testing

* Unit Tests (JUnit, Mockito)
* Controller Tests (MockMvc)
* Integration Testing

---

## 🐳 Running Locally

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🚀 CI/CD Pipeline

Create file:

```
.github/workflows/ci-cd.yml
```

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: ["main"]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v3

      - name: Set up Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build
        run: mvn clean install

      - name: Run Tests
        run: mvn test

      - name: Build Docker Image
        run: docker build -t microservice-app .

      - name: Login DockerHub
        run: echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin

      - name: Push Image
        run: |
          docker tag microservice-app your-dockerhub-username/microservice-app
          docker push your-dockerhub-username/microservice-app

      - name: Deploy (Optional - EC2)
        run: echo "Deploy step placeholder"
```

---

## ☁️ Deployment Strategy (AWS)

### Option 1: EC2 (Simplest)

* Deploy Docker containers on EC2
* Use Nginx as reverse proxy

### Option 2: ECS (Recommended)

* Container orchestration
* Auto-scaling support
* Integrates with ALB

### Option 3: Serverless (Advanced)

* AWS Lambda (for lightweight services)
* API Gateway
* Fully managed infrastructure

---

## 📊 Observability

| Tool       | Purpose             |
| ---------- | ------------------- |
| Actuator   | Health + Metrics    |
| Prometheus | Metrics scraping    |
| Jaeger     | Distributed tracing |

---

## 🚧 Future Enhancements

* Redis caching
* Rate limiting
* Multi-channel notifications (SMS, WhatsApp)
* Batch processing (cron jobs)

---

## 🎯 What This Project Demonstrates

* Real-world microservices design
* Event-driven architecture
* AWS messaging patterns
* Failure handling strategies
* Observability in distributed systems

---

## 👤 Author

**sumanth**
Java Backend Developer

---

## ⭐ Final Note

This is a production-focused backend system solving distributed system challenges such as retries, failures, and observability.

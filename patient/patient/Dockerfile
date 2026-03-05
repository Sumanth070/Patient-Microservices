FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn -B dependency:go-offline

# copy source
COPY src ./src

# build application
RUN mvn clean package -DskipTests


# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# create non-root user
RUN useradd -ms /bin/bash springuser

# copy jar from builder stage
COPY --from=builder /build/target/*.jar app.jar

USER springuser

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
# syntax=docker/dockerfile:1.7

# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# copy pom first for dependency caching
COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B dependency:go-offline

# copy source
COPY src ./src

# build jar
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests



# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
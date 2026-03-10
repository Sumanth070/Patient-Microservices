# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# copy pom first for dependency caching
COPY pom.xml .

RUN mvn -B dependency:go-offline

# copy application source
COPY src ./src

# build the application
RUN mvn clean package -DskipTests


# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# create non-root user
RUN useradd -ms /bin/bash springuser

# copy jar from build stage
COPY --from=builder /build/target/*.jar app.jar

# change ownership
RUN chown springuser:springuser /app/app.jar

USER springuser

EXPOSE 8082

ENTRYPOINT ["java","-jar","app.jar"]
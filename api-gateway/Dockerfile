FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

RUN useradd -ms /bin/bash springuser

COPY --from=builder /build/target/*.jar app.jar

USER springuser

EXPOSE 9090

ENTRYPOINT ["java","-jar","app.jar"]
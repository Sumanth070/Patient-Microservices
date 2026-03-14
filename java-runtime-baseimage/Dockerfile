FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# create non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# install curl for health checks
RUN apk add --no-cache curl

# JVM tuning for containers
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

# enable OpenTelemetry auto instrumentation support
ENV OTEL_SERVICE_NAME="spring-service"
ENV OTEL_TRACES_EXPORTER="otlp"
ENV OTEL_METRICS_EXPORTER="none"
ENV OTEL_LOGS_EXPORTER="none"

USER spring

EXPOSE 9292

HEALTHCHECK --interval=30s --timeout=5s \
CMD curl -f http://localhost:9292/actuator/health || exit 1
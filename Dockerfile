FROM maven:3.9-eclipse-temurin-25-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:25-jre-alpine AS runtime

RUN adduser -S -u 1001 jarvis

RUN apk add --no-cache curl

WORKDIR /app

RUN mkdir -p /app/logs && \
    chown -R jarvis /app

COPY --from=build /app/target/*.jar app.jar

RUN chown jarvis app.jar

USER jarvis

EXPOSE 8080

ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
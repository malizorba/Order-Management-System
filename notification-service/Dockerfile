FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/notification-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

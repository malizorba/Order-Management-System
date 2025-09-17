FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/inventory-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

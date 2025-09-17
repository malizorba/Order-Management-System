# Temel image olarak OpenJDK 21 kullanıyoruz
FROM eclipse-temurin:21-jdk-jammy

# Çalışma dizini
WORKDIR /app

# Maven build sonrası JAR dosyasını kopyala
COPY target/order-service-1.0.0.jar app.jar

# Container çalıştırıldığında JAR'ı çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]

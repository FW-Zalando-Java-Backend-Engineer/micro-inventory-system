# ---------------------------------------------
# Build stage: Use Maven + JDK 17 to compile the app
# ---------------------------------------------

FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom and download dependencies first (cache efficient)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---------------------------------------------
# Runtime stage: Use lightweight JDK 17 image
# ---------------------------------------------

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy only the fat JAR from build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose app port (as defined in application.properties)
EXPOSE 8586

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
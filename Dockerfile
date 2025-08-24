# Stage 1: Build with Maven
FROM maven:3.9.3-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK only
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]

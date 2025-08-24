# Step 1: Use Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Step 4: Build project using Maven
RUN ./mvnw clean package -DskipTests

# Step 5: Copy generated jar
COPY target/*.jar app.jar

# Step 6: Expose port
EXPOSE 8080

# Step 7: Run the application
ENTRYPOINT ["java","-jar","app.jar"]

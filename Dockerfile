# Use official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port (Render provides PORT dynamically)
EXPOSE 8080

# Run the jar
CMD ["sh", "-c", "java -jar target/*.jar --server.port=$PORT"]

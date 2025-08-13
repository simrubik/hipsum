FROM openjdk:17-alpine
#ENV CONTEXT_PATH=/api/betvictor

# Use official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /hipsum

# Add built jar
COPY app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080

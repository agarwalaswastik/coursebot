# Use a base image with JDK
FROM openjdk:17-alpine

RUN apk update && apk add findutils

# Set the working directory
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle settings.gradle gradlew /app/

# Copy the source code
COPY src /app/src

# Copy Gradle wrapper files
COPY gradle /app/gradle

# Run the Gradle build
RUN ./gradlew clean build -x test

# Expose the port your application runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "build/libs/coursebot-0.0.1-SNAPSHOT.jar"]
# Use a Maven base image with OpenJDK 11 for building
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project descriptor files
COPY pom.xml ./
COPY src ./src/

# Build the Spring Boot application
RUN mvn clean package

# Use an OpenJDK base image for running
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file from the build stage into the container
COPY --from=build /app/target/property-api.jar ./property-api.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Run the Spring Boot application when the container launches
CMD ["java", "-jar", "property-api.jar"]
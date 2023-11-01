# Use an official Maven runtime as a parent image
FROM maven:3.8.4-openjdk-17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file into the container
COPY pom.xml .
COPY src/ ./app/

# Build the Maven project
RUN mvn clean install -e

# Expose the port your application will run on (adjust as needed)
EXPOSE 8080

# Define the command to run your application
CMD ["java","-Dspring.thin.layertools=true", "-jar", "target/ecommerce-0.0.1-SNAPSHOT.jar"]

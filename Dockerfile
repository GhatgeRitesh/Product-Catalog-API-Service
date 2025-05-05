# stage 1:  Build the application

FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# copy only necessary files for caching dependencies

COPY pom.xml .
COPY src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests && cp target/*.jar app.jar

# stage 2: Create minimal image with only jar
FROM gcr.io/distroless/java17

WORKDIR /app

# copy only jar file from previous build 
COPY --from=builder /app/app.jar app.jar

# Run the jar file 

ENTRYPOINT ["java","-jar","app.jar"]


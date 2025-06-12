# Stage 1: Build the application
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test --no-daemon
RUN ls /app/build/libs/  # Kiểm tra tệp JAR được tạo ra

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
RUN ls /app  # Kiểm tra tệp JAR có mặt ở đây
EXPOSE 9999
CMD ["java", "-jar", "app.jar"]

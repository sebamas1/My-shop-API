FROM openjdk:17-jdk-alpine

WORKDIR /app-server

COPY build/libs/*.jar app-server.jar

CMD ["java", "-jar", "app-server.jar"]

EXPOSE 3000

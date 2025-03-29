FROM openjdk:25-slim-bookworm

WORKDIR /app

COPY build/libs/assignment-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
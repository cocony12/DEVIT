FROM openjdk:8-jdk-alpine

VOLUME /var/lib/ubuntu/uploads

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
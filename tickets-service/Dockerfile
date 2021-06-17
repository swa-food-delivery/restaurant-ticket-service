FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tickets.jar
ENTRYPOINT ["java", "-jar", "/tickets.jar"]

LABEL org.label-schema.name="tickets-service"
LABEL org.label-schema.description="Docker image for gateway"
LABEL org.label-schema.docker.cmd="docker run --network='host' tickets-service"
FROM openjdk:11-jdk-slim
VOLUME /tmp
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-agentlib:jdwp=transport=dt_socket,server=y,address=5005,suspend=n", "-Dspring.profiles.active=docker", "-jar","/app.jar"]
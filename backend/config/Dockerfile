FROM eclipse-temurin:23-jdk-alpine

WORKDIR /config

COPY ./build/libs/config-1.0.0.jar ./config.jar

EXPOSE 8080

CMD ["java", "-jar", "config.jar"]
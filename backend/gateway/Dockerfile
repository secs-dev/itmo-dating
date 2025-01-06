FROM eclipse-temurin:23-jdk-alpine

WORKDIR /gateway

COPY ./build/libs/gateway-1.0.0.jar ./gateway.jar

EXPOSE 8080

CMD ["java", "-jar", "gateway.jar"]
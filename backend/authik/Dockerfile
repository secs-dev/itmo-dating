FROM eclipse-temurin:21-jdk-alpine

WORKDIR /matchmaker

COPY ./build/libs/authik-1.0.0.jar ./authik.jar

EXPOSE 8080

CMD ["java", "-jar", "authik.jar"]
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /people

COPY ./build/libs/people-1.0.0.jar ./people.jar

EXPOSE 8080

CMD ["java", "-jar", "people.jar"]
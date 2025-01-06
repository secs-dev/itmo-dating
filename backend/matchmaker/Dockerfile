FROM eclipse-temurin:23-jdk-alpine

WORKDIR /matchmaker

COPY ./build/libs/matchmaker-1.0.0.jar ./matchmaker.jar

EXPOSE 8080

CMD ["java", "-jar", "matchmaker.jar"]
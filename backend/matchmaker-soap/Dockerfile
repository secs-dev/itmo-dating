FROM eclipse-temurin:23-jdk-alpine

WORKDIR /matchmaker-soap

COPY ./build/libs/matchmaker-soap-1.0.0.jar ./matchmaker-soap.jar

EXPOSE 8080

CMD ["java", "-jar", "matchmaker-soap.jar"]
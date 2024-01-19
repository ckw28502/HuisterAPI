FROM eclipse-temurin:17
LABEL authors="CALVIN"

WORKDIR /app

COPY build/libs/*.jar app.jar
COPY src/main/resources/application-prod.properties application.properties


EXPOSE 8080

CMD ["java","-jar","app.jar"]

# run ./gradlew clean build --no-daemon
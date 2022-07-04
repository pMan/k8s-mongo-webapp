FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /app

COPY ${JAR_FILE} app.jar

CMD ["java","-jar","app.jar"]

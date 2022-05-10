FROM maven:3.6.1-jdk-8-slim AS build
RUN mkdir -p workspace
WORKDIR workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY news-parser-frontend /workspace/news-parser-frontend
RUN mvn -f pom.xml clean install -DskipTests=true


FROM openjdk:17
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","app.jar"]
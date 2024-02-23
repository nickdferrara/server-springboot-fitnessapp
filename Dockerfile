
FROM docker.io/library/gradle:8.4-jdk17-alpine as builder
LABEL authors="Nicholas Ferrara"

ADD . /project
WORKDIR /project
RUN gradle build --no-daemon --stacktrace

FROM docker.io/azul/zulu-openjdk-alpine:17-jre-latest

ARG JAR_FILE=/project/build/libs/api-posjournal-0.0.1.jar
COPY --from=builder ${JAR_FILE} /app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
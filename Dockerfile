FROM openjdk:15-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /home/app/config

WORKDIR /home/app
ENTRYPOINT ["java","-jar","/app.jar"]
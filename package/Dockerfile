FROM openjdk:8-jre-alpine

RUN apk update
COPY weather-service.yml /
COPY weather-service-0.1-SNAPSHOT.jar /

ENTRYPOINT ["java"]

CMD ["-jar","weather-service-0.1-SNAPSHOT.jar","server","./weather-service.yml"]
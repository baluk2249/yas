FROM ubuntu:latest AS build
WORKDIR /yas
RUN apt-get update && apt-get install -y maven openjdk-21-jdk
COPY pom.xml /yas/
COPY . /yas/
RUN mvn clean install -pl cart -am


FROM eclipse-temurin:21-jre-alpine
COPY --from=build /yas/cart/target/cart*.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]

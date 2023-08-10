# node
FROM node:16 AS front-build

WORKDIR /app

COPY web .

RUN npm -v && npm install && npm run build


# maven
FROM maven:3.9.0-amazoncorretto-17 AS build

WORKDIR /app

COPY src src
COPY pom.xml pom.xml
COPY --from=front-build /app/dist src/main/resources/static
RUN mvn -f pom.xml clean package -Dmaven.test.skip=true

# JRE
FROM amazoncorretto:17.0.6-alpine as corretto-jdk

# required for strip-debug to work
RUN apk add --no-cache binutils

# Build small JRE image
RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre

#
# Package stage
#
FROM alpine:latest

ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# copy JRE from the base image
COPY --from=corretto-jdk /customjre $JAVA_HOME

ENV SERVER_PORT 8080
ENV DEFAULT_USERNAME admin
ENV DEFAULT_PASSWORD admin

WORKDIR /usr/local/zookeeper-admin

COPY --from=build /app/target/*.jar zookeeper-admin.jar

EXPOSE $SERVER_PORT

ENTRYPOINT ["/jre/bin/java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/usr/local/zookeeper-admin/zookeeper-admin.jar", "--server.port=${SERVER_PORT}", "--default.username=${DEFAULT_USERNAME}", "--default.password=${DEFAULT_PASSWORD}"]

FROM openjdk:11-jdk
MAINTAINER chanyeol@edu.hanbat.ac.kr

WORKDIR /
ADD ./build/libs/sia-backend-*.jar app.jar

EXPOSE 8083

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
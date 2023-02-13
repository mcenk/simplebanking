
FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=build/libs/simplebanking-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} simplebankingDocker.jar
ENTRYPOINT ["java","-jar","/simplebankingDocker.jar"]
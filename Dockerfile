FROM openjdk:11-jre-slim-buster
COPY target/*.jar erate-assignment.jar
ENTRYPOINT ["java","-jar","/erate-assignment.jar"]
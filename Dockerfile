FROM openjdk:17-jdk-slim
COPY target/*.jar test.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/test.jar"]
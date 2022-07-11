FROM openjdk:11-jre-slim-buster
Add ./employee-managment-system.jar  employee-managment-system.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","employee-managment-system.jar"]
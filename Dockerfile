FROM openjdk:11
LABEL maintainer = "volunteer-registration"
add target/springboot-thymeleaf-crud-webapp-0.0.1-SNAPSHOT.jar volunteer-registration.jar
ENTRYPOINT ["java", "-jar", "volunteer-registration.jar"]
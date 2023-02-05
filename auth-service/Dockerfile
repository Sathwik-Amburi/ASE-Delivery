FROM amazoncorretto:19
ADD target/auth-service.jar auth-service.jar
#EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
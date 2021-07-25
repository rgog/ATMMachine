FROM openjdk:16
ADD target/docker-spring-boot-atm-machine.jar docker-spring-boot-atm-machine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar","/docker-spring-boot-atm-machine.jar"]

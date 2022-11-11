FROM openjdk:17-ea-6-oracle
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java", "-jar", "spring-boot-docker.jar"]
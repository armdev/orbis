FROM amazoncorretto
#FROM openjdk:11-jdk-slim
COPY target/box.jar /opt/box.jar
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar","/opt/box.jar"]
EXPOSE 4002

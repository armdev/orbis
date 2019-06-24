FROM openjdk:11-jdk-slim
COPY target/payment.jar /opt/payment.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/payment.jar"]
EXPOSE 4003

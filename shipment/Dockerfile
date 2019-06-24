FROM openjdk:11-jdk-slim
COPY target/shipment.jar /opt/shipment.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/shipment.jar"]
EXPOSE 4005

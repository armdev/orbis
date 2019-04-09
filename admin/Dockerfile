FROM amazoncorretto
COPY target/admin.jar /opt/admin.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/admin.jar"]
EXPOSE 1111

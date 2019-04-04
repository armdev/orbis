FROM amazoncorretto
COPY target/marshal.jar /opt/marshal.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/marshal.jar"]
EXPOSE 4007

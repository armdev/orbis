FROM amazoncorretto
COPY target/register.jar /opt/register.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/register.jar"]
EXPOSE 4004

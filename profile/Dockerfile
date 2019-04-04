FROM amazoncorretto
COPY target/profile.jar /opt/profile.jar
ENTRYPOINT ["java",  "-Djava.net.preferIPv4Stack=true",  "-jar","/opt/profile.jar"]
EXPOSE 4006

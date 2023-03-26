FROM amazoncorretto:19
COPY target/argprogback-0.0.1-SNAPSHOT.jar argprogback-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","argprogback-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
ARG JAR_FILE=target/pancakes-unlimited-server-0.0.1-SNAPSHOT.jar

FROM amazoncorretto:17
VOLUME /tmp
EXPOSE 8080
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

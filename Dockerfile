FROM openjdk:11
ADD ./target/muzix-application-mongodb-0.0.1-SNAPSHOT.jar /usr/src/muzix-application-mongodb-0.0.1-SNAPSHOT.jar
EXPOSE 8090
WORKDIR usr/src
ENTRYPOINT ["java","-jar","muzix-application-mongodb-0.0.1-SNAPSHOT.jar"]


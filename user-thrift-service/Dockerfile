FROM openjdk:8-jdk
MAINTAINER user-service w@wktadmin.com

COPY target/user-thrift-service-1.0-SNAPSHOT.jar /user-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar"]
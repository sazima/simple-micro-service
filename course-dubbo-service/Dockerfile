FROM openjdk:8-jdk
MAINTAINER course-dubbo-service w@wktadmin.com
WORKDIR /
ADD https://raw.githubusercontent.com/sazima/simple-micro-service/master/wait-for-it.sh .
RUN chmod +x wait-for-it.sh
COPY target/course-dubbo-service-1.0-SNAPSHOT.jar /fk.jar
ENTRYPOINT ["java", "-jar", "/fk.jar"]
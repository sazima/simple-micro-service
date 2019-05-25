FROM openjdk:8-jdk
MAINTAINER course-dubbo-service w@wktadmin.com
EXPOSE 8889

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar
ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]
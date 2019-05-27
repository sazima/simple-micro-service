#!/usr/bin/env bash
path="$(cd "$(dirname $0)";pwd)"
mvn clean install
docker build -t hub.wktadmin.com:11111/micro-service/course-dubbo-service:latest $path
docker push hub.wktadmin.com:11111/micro-service/course-dubbo-service:latest
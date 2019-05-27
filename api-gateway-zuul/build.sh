#!/usr/bin/env bash
path="$(cd "$(dirname $0)";pwd)"
mvn clean install
docker build -t hub.wktadmin.com:11111/micro-service/api-gateway-zuul:latest $path/
docker push hub.wktadmin.com:11111/micro-service/api-gateway-zuul:latest
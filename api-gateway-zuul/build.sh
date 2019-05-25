#!/usr/bin/env bash

mvn clean install
docker build -t hub.imooc.com:11111/micro-service/api-gateway-zuul:latest .
docker push hub.imooc.com:11111/micro-service/api-gateway-zuul:latest
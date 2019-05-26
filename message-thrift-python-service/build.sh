#!/usr/bin/env bash
docker build -t hub.imooc.com:11111/micro-service/message-thrift:latest .
docker push hub.imooc.com:11111/micro-service/message-thrift:latest

#!/usr/bin/env bash
path="$(cd "$(dirname $0)";pwd)"
docker build -t hub.wktadmin.com:11111/micro-service/message-thrift:latest $path/
docker push hub.wktadmin.com:11111/micro-service/message-thrift:latest

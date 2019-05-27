#!/usr/bin/env bash
sh api-gateway-zuul/build.sh
sh course-dubbo-service/build.sh
sh course-edge-service/build.sh
sh message-thrift-python-service/build.sh
sh user-edge-service/build.sh
sh user-thrift-service/build.sh
sh envfile
docker stack deploy -c service.yml services
#!/usr/bin/env bash
export MYSQL_ADDRESS=10.0.75.1
export REDIS_HOST=10.0.75.1
export MYSQL_PASSWORD=
export MAIL_PASS=

bash api-gateway-zuul/build.sh
bash course-dubbo-service/build.sh
bash course-edge-service/build.sh
bash message-thrift-python-service/build.sh
bash user-edge-service/build.sh
bash user-thrift-service/build.sh
docker stack deploy -c service.yml services
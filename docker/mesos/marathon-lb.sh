#!/usr/bin/env bash
MARATHON_HOST=192.168.0.110:8090
docker stop marathon-lb
docker rm marathon-lb

docker run -d -p 9090:9090 \
    -e PORTS=9090 --name marathon-lb \
    mesosphere/marathon-lb sse \
    --marathon http://$MARATHON_HOST \
     --group external --strict-mode --health-check
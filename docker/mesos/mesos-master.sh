#!/usr/bin/env bash
docker stop mesos-master
docker rm mesos-master
docker run -d --net=host \
  -e MESOS_PORT=5050 \
  -e MESOS_ZK=zk://127.0.0.1:2181/mesos \
  -e MESOS_QUORUM=1 \
  -e MESOS_REGISTRY=in_memory \
  -e MESOS_LOG_DIR=/var/log/mesos \
  -e MESOS_WORK_DIR=/var/tmp/mesos \
  -v "$(pwd)/log/mesos:/var/log/mesos" \
  -v "$(pwd)/work/mesos:/var/tmp/mesos" \
  --name mesos-master \
  mesosphere/mesos-master:1.4.1
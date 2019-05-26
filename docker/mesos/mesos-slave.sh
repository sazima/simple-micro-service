#!/usr/bin/env bash
SLAVE_HOST=192.168.0.110
ZK_HOST=192.168.0.110


docker stop mesos-slave
docker rm mesos-slave
docker run -d --net=host --privileged \
  --hostname $SLAVE_HOST \
  -e MESOS_PORT=5051 \
  -e MESOS_MASTER=zk://$ZK_HOST:2181/mesos \
  -e MESOS_SWITCH_USER=0 \
  -e MESOS_CONTAINERIZERS=docker,mesos \
  -e MESOS_LOG_DIR=/var/log/mesos \
  -e MESOS_WORK_DIR=/var/work/mesos \
  -v "$(pwd)/log/mesos_slave:/var/log/mesos" \
  -v "$(pwd)/tmp/mesos_slave:/var/tmp/mesos" \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v /sys:/sys \
  -v /usr/local/bin/docker:/usr/local/bin/docker \
  --name mesos-slave \
  mesosphere/mesos-slave:1.4.1 --no-systemd_enable_support  \
   --no-hostname_lookup --ip=$SLAVE_HOST
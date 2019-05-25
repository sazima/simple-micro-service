#!/usr/bin/env bash

mvn clean install
docker build -t course-dubbo-service:latest .
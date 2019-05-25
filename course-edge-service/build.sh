#!/usr/bin/env bash

mvn clean install
docker build -t course-edge-service:latest .
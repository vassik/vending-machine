#!/bin/sh
IMAGE_NAME=local/rvm
docker rmi --force ${IMAGE_NAME}
docker build -t ${IMAGE_NAME} . && docker run -it -p 8080:8080 ${IMAGE_NAME}
#!/bin/bash

mvn -P prod clean install docker:build
docker push berndpb/chess-service2
cf push chess-service2-stage --docker-image=berndpb/chess-service2

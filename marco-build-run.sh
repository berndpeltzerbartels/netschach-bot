#!/bin/bash

./mvnw -DskipTests -P prod clean install
java -Dserver.port=80 -Dspring.profiles.active=marco -jar target/stockfishchess2-2.0.18.jar

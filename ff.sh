#!/bin/bash

mvn clean install -pl orbis  -am -DskipTests=true

docker-compose up -d --no-deps --build orbis

docker logs --follow orbis



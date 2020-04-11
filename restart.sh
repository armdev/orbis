#!/bin/bash


if [ $# -lt 1 ]
then
        echo "Usage : missing params!!"
        exit
fi


mvn clean install -pl $1  -am -DskipTests=true

docker-compose up -d --no-deps --build $1

docker logs --follow $1



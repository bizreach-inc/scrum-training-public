#!/bin/sh

./wait-for-it.sh db:3306

java -Djava.security.egd=file:/dev/./urandom -Dblabla -jar /project/target/snsppoi-0.0.1-SNAPSHOT.jar
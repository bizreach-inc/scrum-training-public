#!/bin/sh

mvn -Dflyway.url="jdbc:mysql://localhost:3306/snsppoi?autoReconnect=true&useSSL=false" -Dflyway.user=snsppoi_user -Dflyway.password=snsppoi_pass flyway:migrate

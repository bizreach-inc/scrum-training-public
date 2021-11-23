FROM openjdk:8

RUN apt-get update && apt-get install -y maven
WORKDIR /project
COPY . .
#RUN cp -./src/main/resources/application.properties_for_docker ./src/main/resources/
ADD ./src/main/resources/application.properties_for_docker ./src/main/resources/application.properties
# UTはJenkins上で実行しているので、package時にはSkipする
RUN mvn clean package -DskipTests=true

#run the spring boot application

RUN chmod 777 ./entrypoint.sh
RUN chmod 777 ./wait-for-it.sh

ENTRYPOINT ["./entrypoint.sh"]



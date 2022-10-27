FROM debian:stable-slim
RUN apt update && apt -y upgrade
RUN apt install -y openjdk-11-jdk maven
RUN apt clean
RUN rm -rf /var/lib/apt/lists/*

WORKDIR /app
# Copy app files
COPY . .

RUN mvn clean install -f ./webapp/pom.xml

RUN ls ./webapp/target
EXPOSE 3000
# java -jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./webapp/target/webapp-0.0.1-SNAPSHOT.jar","--spring.profiles.active=dev"]
#ENTRYPOINT ["java","-jar","app.jar"]
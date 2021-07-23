# push-message

A small push message system

# Base on [ezyfox-server](https://github.com/youngmonkeys/ezyfox-server)

<img src="https://github.com/youngmonkeys/ezyfox-server/blob/master/logo.png" width="256" />

# Architecture

<img src="https://raw.githubusercontent.com/tvd12/ezyfox-server-example/master/push-message/images/push-message.png" />

** Notice: `api-gateway` is your business server, it contains your business, and you will decide what kind of message here

# Documentation

[https://youngmonkeys.org/get-started/](https://youngmonkeys.org/get-started/)

# Requirement

1. [JDK 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
2. [Maven 3](https://maven.apache.org/download.cgi)
3. [Kafka](https://kafka.apache.org/downloads)

# How to run?

## Start kafka

```bash
# start zookeeper first
bin/zookeeper-server-start.sh config/zookeeper.properties

# then start kafka
bin/kafka-server-start.sh config/server.properties
```

Please refer [kafka quickstart](https://kafka.apache.org/quickstart) to get more guide

## Start socket server

### Build

1. Move to `socket-server` folder
2. Run command `bash build.sh` or

```bash
mvn -Denv.EZYFOX_SERVER_HOME=deploy -Pezyfox-deploy clean install
```

### Run

1. Move to `socket-server/deploy` folder
2. Run command

```bash
# On linux
bash ./console.sh

# On windows
console.bat
```

## Start an api gateway server

You just need start only one api gateway related to your prefer langugage

### start java

### Build

1. Move to `api-gateway-java` folder
2. Run command `bash build.sh` or

```bash
mvn -Denv.EZYFOX_SERVER_HOME=deploy -Pezyfox-deploy clean install
```

### Run

1. Move to `api-gateway-java/deploy` folder
2. Run command

```bash
# On linux
bash ./console.sh

# On windows
console.bat
```

### Start nodejs

1. Move to folder `api-gateway-nodejs`
2. Run `npm i`
3. Run `node index.js`

### Start go

1. MOve to folder `api-gateway-go`
2. Run ` go mod init org.younmonkeys.example/push-message-api-gateway`
3. Run `go mod tidy`
4. Run `go get github.com/segmentio/kafka-go`
5. Run `go install`
6. Run `go .`

## Start UI client

### Start ReactJS web client

1. Move to folder `client-reactjs`
2. Run `npm i`
3. Run `npm start`

# How to test?

### With java api gateway

run:

```bash
curl --location --request POST 'http://localhost:8080/api/v1/message/push' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username" : "dungtv",
    "data" : {
        "title": "Welcome1",
        "content": "Hi Young Monkey!"
    }
}'
```

### With nodejs api gateway

run:

```bash
curl --location --request POST 'http://localhost:8081/api/v1/message/push' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username" : "dungtv",
    "data" : {
        "title": "Welcome1",
        "content": "Hi Young Monkey!"
    }
}'
```

### With go api gateway

run:

```bash
curl --location --request POST 'http://localhost:8082/api/v1/message/push' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username" : "dungtv",
    "data" : {
        "title": "Welcome1",
        "content": "Hi Young Monkey!"
    }
}'
```

### How to implement for your project?

You just need replace `api-gateway` and `UI client` by your application server and your UI client

# License

- Apache License, Version 2.0

# push-message

# Power by [ezyfox-server](https://github.com/youngmonkeys/ezyfox-server)

<img src="https://github.com/youngmonkeys/ezyfox-server/blob/master/logo.png" width="256" />

# Architecture

<img src="https://raw.githubusercontent.com/tvd12/ezyfox-server-example/master/push-message/images/push-message.png" />

# Documentation

[https://youngmonkeys.org/get-started/](https://youngmonkeys.org/get-started/)

# Requirement

1. [JDK 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
2. [Maven 3](https://maven.apache.org/download.cgi)
3. [Kafka](https://kafka.apache.org/downloads)

# How to test?

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

## Start a api gateway server

You just need start only one api gateway related to your prefer langugage

### Start nodejs

1. Move to folder `api-gateway-nodejs`
2. Run `node index.js`

# License

- Apache License, Version 2.0

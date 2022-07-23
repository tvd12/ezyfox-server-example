#!/bin/bash
home=$(pwd)
cd hello-world/clients/android
./gradlew build
cd $home
cd hello-world/server
mvn clean install
cd $home
cd lucky-wheel
mvn clean install
cd $home
cd message-stresstest
mvn clean install
cd $home
cd one-two-three
mvn clean install
cd $home
cd push-message/api-gateway-java
mvn clean install
cd $home
cd push-message/client-android
./gradlew build
cd $home
cd push-message/socket-server
mvn clean install
cd $home
cd simple-chat
mvn clean install
cd $home
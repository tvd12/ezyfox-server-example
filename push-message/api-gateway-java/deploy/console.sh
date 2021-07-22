#!/bin/sh

EZYFOX_SERVER_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo 'current dir: ' $EZYFOX_SERVER_HOME
CLASSPATH="lib/*:common/*:apps/common/*:apps/resources/*"
for dir in "$EZYFOX_SERVER_HOME"/plugins/*
do
  CLASSPATH="$CLASSPATH:plugins/${dir##*/}/*"
done
echo 'classpath: ' $CLASSPATH
java $1 -cp $CLASSPATH org.youngmonkeys.example.push_message.gateway.api.PushMessageApiGatewayStartup
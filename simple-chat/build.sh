#export EZYFOX_SERVER_HOME=
mvn -pl . clean install
mvn -pl simple-chat-common -Pexport clean install
mvn -pl simple-chat-app-api -Pexport clean install
mvn -pl simple-chat-app-entry -Pexport clean install
mvn -pl simple-chat-plugin -Pexport clean install
cp simple-chat-zone-settings.xml $EZYFOX_SERVER_HOME/settings/zones/

#export EZYFOX_SERVER_HOME=
mvn -pl . clean install
mvn -pl one-two-three-common -Pexport clean install
mvn -pl one-two-three-app-api -Pexport clean install
mvn -pl one-two-three-app-entry -Pexport clean install
mvn -pl one-two-three-plugin -Pexport clean install
cp one-two-three-zone-settings.xml $EZYFOX_SERVER_HOME/settings/zones/

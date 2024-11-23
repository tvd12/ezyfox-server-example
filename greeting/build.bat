:: set EZYFOX_SERVER_HOME=
mvn -pl . clean install & ^
mvn -pl greeting-common -Pexport clean install & ^
mvn -pl greeting-app-api -Pexport clean install & ^
mvn -pl greeting-app-entry -Pexport clean install & ^
mvn -pl greeting-plugin -Pexport clean install & ^
copy greeting-zone-settings.xml %EZYFOX_SERVER_HOME%/settings/zones/

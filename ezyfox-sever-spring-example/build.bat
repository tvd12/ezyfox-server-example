:: set EZYFOX_SERVER_HOME=
mvn -pl . clean install & ^
mvn -pl ezyfox-sever-spring-example-common -Pexport clean install & ^
mvn -pl ezyfox-sever-spring-example-app-api -Pexport clean install & ^
mvn -pl ezyfox-sever-spring-example-app-entry -Pexport clean install & ^
mvn -pl ezyfox-sever-spring-example-plugin -Pexport clean install & ^
copy ezyfox-sever-spring-example-zone-settings.xml %EZYFOX_SERVER_HOME%/settings/zones/

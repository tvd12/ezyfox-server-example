
#!/bin/sh

EZYFOX_SERVER_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo 'current dir: ' $EZYFOX_SERVER_HOME
CLASSPATH="lib/*:settings"
echo 'classpath: ' $CLASSPATH
java $1 -cp $CLASSPATH org.youngmonkeys.ezyfox_server_spring_example.ApplicationStartup

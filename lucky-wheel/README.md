# Luck Wheel

A simple lucky-wheel game use [ezyfox-server](https://github.com/youngmonkeys/ezyfox-server) and [phaser](https://phaser.io/) client

# Documentation

1. [ezyfox-server](https://youngmonkeys.org/project/ezyfox-sever/)
2. [websocket client](https://github.com/youngmonkeys/ezyfox-server-js-client)

# Required

1. Java 1.8+
2. Intellij or Eclipse

# How to run?

1. Download [lucky-wheel](https://github.com/tvd12/ezyfox-server-example/tree/master/lucky-wheel) from Github
2. Import it to your IDE
3. Run [ServerStartup](https://github.com/tvd12/ezyfox-server-example/blob/master/lucky-wheel/src/main/java/com/tvd12/example/lucky_wheel/ServerStartup.java) to start server
4. Run [ClientStartup](https://github.com/tvd12/ezyfox-server-example/blob/master/lucky-wheel/src/main/java/com/tvd12/example/lucky_wheel/ClientStartup.java) to start client
5. Open [http://localhost:8080/index.html](http://localhost:8080/index.html) in your browser and play

# How to deploy to ezyfox-server?

1. Download [ezyfox-sever full version](https://resources.tvd12.com/)
2. Setup `EZYFOX_SERVER_HOME` environment variable: let's say you place `ezyfox-server` at `/Programs/ezyfox-server` so `EZYFOX_SERVER_HOME = /Programs/ezyfox-server`
3. Run `build.sh` file on your terminal
4. Copy `lucky-wheel-zone-settings.xml` to `EZYFOX_SERVER_HOME/settings/zones` folder
5. Open file `EZYFOX_SERVER_HOME/settings/ezy-settings.xml` and add to `<zones>` tag:

```xml
    <zone>
        <name>lucky-wheel</name>
        <config-file>lucky-wheel-zone-settings.xml</config-file>
        <active>true</active>
    </zone>
```

6. Run `console.sh` in `EZYFOX_SERVER_HOME` on your termial, if you want to run `ezyfox-server` in backgroud you will need run `start-server.sh` on your terminal

# Need a support?

If you need a support please left an issue or contact to me

# Thanks

Many thank to [vanquyet94](https://github.com/vanquyet94/wheel-lucky-draw) for phaser client
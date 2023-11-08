using System;
using System.Threading;
using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.evt;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.config;
using com.tvd12.ezyfoxserver.client.setup;
using com.tvd12.ezyfoxserver.client.handler;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.factory;

namespace hello_csharp
{
    public class FreeMmoTest
    {
        public void Run()
        {
            EzyClientConfig config = EzyClientConfig.builder()
                .zoneName("free-game-server")
                .enableDebug()
                .enableSSL(true)
                .build();
            EzyClient client = new EzyUTClient(config);
            setupClient(client);
            EzyClients.getInstance().addClient(client);
            client.connect("178.63.237.228", 3005);
        }

        public void setupClient(EzyClient client)
        {
            EzySetup setup = client.setup();
            setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, new EzyConnectionSuccessHandler());
            setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, new EzyConnectionFailureHandler());
            setup.addEventHandler(EzyEventType.DISCONNECTION, new EzyDisconnectionHandler());
            setup.addDataHandler(EzyCommand.HANDSHAKE, new FreeMmoHandshakeEventHandler());
            setup.addDataHandler(EzyCommand.LOGIN, new FreeMmoLoginSuccessHandler());
            setup.addDataHandler(EzyCommand.UDP_HANDSHAKE, new FreeMmoUdpHanshakeHandler());
            setup.addDataHandler(EzyCommand.APP_ACCESS, new FreeMmoAccessAppHandler());
           

            EzyAppSetup appSetup = setup.setupApp("free-game-server");
            appSetup.addDataHandler(Commands.ERROR, new FreeMmoErrorHandler());
            appSetup.addDataHandler("characterList", new FreeMmoCharacterListHandler());
            appSetup.addDataHandler("createCharacter", new FreeMmmoCreateCharacterHandler());
            appSetup.addDataHandler("play", new FreeMmoPlayHandler());
            appSetup.addDataHandler("reconnect", new FreeMmoReconnectHandler());
            appSetup.addDataHandler("s", new FreeMmoSyncPositionHandler());
        }
    }

    class FreeMmoHandshakeEventHandler : EzyHandshakeHandler
    {

        protected override EzyRequest getLoginRequest()
        {
            return new EzyLoginRequest("free-game-server", "Assambra", "123456");
        }
    }

    class FreeMmoLoginSuccessHandler : EzyLoginSuccessHandler
    {

        protected override void handleLoginSuccess(EzyData responseData)
        {
            client.udpConnect(2611);
        }
    }

    class FreeMmoUdpHanshakeHandler : EzyUdpHandshakeHandler
    {
        protected override void onAuthenticated(EzyArray data)
        {
            client.send(new EzyAppAccessRequest("free-game-server"));
        }
    }

    class FreeMmoAccessAppHandler : EzyAppAccessHandler
    {

        protected override void postHandle(EzyApp app, EzyArray data)
        {
            Console.WriteLine("accep app: " + data);
            app.send(
                "createCharacter",
                EzyEntityFactory.newObjectBuilder()
                    .append("name", "Dung")
                    .append("sex", "male")
                    .append("race", "test")
                    .append("model", "test")
                    .build()
            );
        }
    }

    class FreeMmoErrorHandler : EzyAppDataHandler
    {

        public void handle(EzyApp app, EzyData data)
        {
            Console.WriteLine("error: " + data);
        }
    }

    class FreeMmoCharacterListHandler : EzyAppDataHandler
    {

        public void handle(EzyApp app, EzyData data)
        {
            EzyArray characterList = (EzyArray)data;
            if (characterList.size() > 0)
            {
                Console.WriteLine("start to play");
                app.send(
                    "play",
                    EzyEntityFactory
                        .newObjectBuilder()
                        .append(
                            "characterId",
                            characterList
                                .get<EzyArray>(0)
                                .get<int>(0))
                                .build()
                );
            }
        }
    }

    class FreeMmmoCreateCharacterHandler : EzyAppDataHandler
    {
        public void handle(EzyApp app, EzyData data)
        {
            Console.WriteLine("createCharacter: " + data);
            EzyObject objData = (EzyObject)data;
            String result = objData.get<string>("result");
            if ("name_already_in_use".Equals(result))
            {
                app.send("characterList");
            }
            else
            {
                app.send(
                    "play",
                    EzyEntityFactory
                        .newObjectBuilder()
                        .append("characterId", objData.get<int>("characterId"))
                        .build()
                );
            }
        }
    }

    class FreeMmoPlayHandler : EzyAppDataHandler
    {
        public void handle(EzyApp app, EzyData data)
        {
            Console.WriteLine("play: " + data);
            FreeMmoScheduler.startSyncPositionSchedule(app);
        }
    }

    class FreeMmoReconnectHandler : EzyAppDataHandler
    {
        public void handle(EzyApp app, EzyData data)
        {
            Console.WriteLine("reconnect: " + data);
            FreeMmoScheduler.startSyncPositionSchedule(app);
        }
    }

    class FreeMmoSyncPositionHandler : EzyAppDataHandler
    {
        public void handle(EzyApp app, EzyData data)
        {
            Console.WriteLine("syn pos: " + data);
        }
    }

    class FreeMmoScheduler
    {
        public static void startSyncPositionSchedule(EzyApp app)
        {
            Thread newThread = new Thread(() =>
            {
                while(true)
                {
                    Thread.Sleep(1000);
                    app.udpSend(
                        "playerInput",
                        EzyEntityFactory
                            .newObjectBuilder()
                            .append("t", 1)
                            .append("i", new bool[] { true, false, true, false })
                            .append("r", new float[] { 1.0F, 1.0F, 1.0F })
                            .build()
                    );
                }
            });
            newThread.Start();
        }
    }
}
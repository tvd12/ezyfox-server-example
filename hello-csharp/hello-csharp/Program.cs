﻿using System;
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

using com.tvd12.ezyfoxserver.client.logger;
using com.tvd12.ezyfoxserver.client.socket;
using com.tvd12.ezyfoxserver.client.concurrent;

namespace hello_csharp
{
    public sealed class Commands
    {

        public static readonly String ERROR = "err";
        public static readonly String GREET = "greet";
        public static readonly String SECURE_CHAT = "secureChat";
        public static readonly String ROOM_INFO = "roomInfo";
        public static readonly String CREATE_ROOM = "createRoom";
        public static readonly String LEAVE_ROOM = "leaveRoom";
        public static readonly String JOIN_ROOM = "joinRoom";
        public static readonly String JOIN_ROOM_RANDOM = "joinRoomRandom";
        public static readonly String JOIN_OR_CREATE_ROOM = "joinOrCreateRoom";
        public static readonly String SYNC_POSITION = "p";
        public static readonly String ACCESS_LOBBY_ROOM = "accessLobbyRoom";
        public static readonly String RECONNECT = "reconnect";
        public static readonly String RECONNECT_FAILED = "reconnectFailed";

        private Commands()
        {
        }
    }

    class MainClass
    {
        public static void Main(string[] args)
        {
            Thread.CurrentThread.Name = "main";

            new RsaExample().Run();
            new RsaExampleCloneJava().Run();
            new RsaDecryptExample().Run();
            new BindingExample().Run();
            new EntityExample().Run();
            new DateTimeExample().Run();
            new MsgPackExample().Run();
            new SupportExample().Run();
            //new FreeMmoTest().Run();

            EzyLoggerFactory.setLoggerLevel(EzyLoggerLevel.DEBUG);

            EzyClientConfig clientConfig = EzyClientConfig
                .builder()
                //.enableSSL(true)
                .clientName("freetanks")
                .zoneName("example")
                .build();
            EzyClients clients = EzyClients.getInstance();
            //EzyClient client = clients.newDefaultClient(clientConfig);
            EzyEventLoopGroup eventLoopGroup = new EzyEventLoopGroup(3);
            EzyClient client = new EzyUTClient(clientConfig, eventLoopGroup);
            clients.addClient(client);
            EzySetup setup = client.setup();
            setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, new EzyConnectionSuccessHandler());
            setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, new EzyConnectionFailureHandler());
            setup.addEventHandler(EzyEventType.DISCONNECTION, new EzyDisconnectionHandler());
            setup.addDataHandler(EzyCommand.HANDSHAKE, new ExHandshakeEventHandler());
            setup.addDataHandler(EzyCommand.LOGIN, new ExLoginSuccessHandler());
            //setup.addDataHandler(EzyCommand.LOGIN_ERROR, new ExLoginErrorHandler());
            setup.addDataHandler(EzyCommand.APP_ACCESS, new ExAccessAppHandler());
            setup.addDataHandler(EzyCommand.UDP_HANDSHAKE, new UdpHandshakeHandler());

            EzyAppSetup appSetup = setup.setupApp("hello-world");
            //appSetup.addDataHandler(Commands.ERROR, new ErrorResponseHandler());
            appSetup.addDataHandler(Commands.ACCESS_LOBBY_ROOM, new AccessLobbyResponseHandler());
            appSetup.addDataHandler(Commands.ROOM_INFO, new RoomInfoResponseHandler());
            appSetup.addDataHandler(Commands.SYNC_POSITION, new SyncPositionHandler());

            //client.connect("ws.tvd12.com", 3005);
            client.connect("127.0.0.1", 3005);

            //int time = 0;

            //while (true)
            //{
            //    Thread.Sleep(3);
            //    client.processEvents();
            //    time += 3;
            //    if (time > 5000)
            //    {
            //client.disconnect(401);
            //time = 0;
            //break;
            //}
            // Console.WriteLine(client.getNetworkStatistics().getSocketStats().getNetworkStats().getReadBytes());
            //}

            mainEventsLoopTest();
        }

        private static void mainEventsLoopTest()
        {
            //Thread.Sleep(3000);

            //Console.WriteLine("client shutted down");

            EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop();
            mainEventsLoop.start();

            //IList<EzyClient> cachedClients = new List<EzyClient>();

            //while(true) 
            //{
            //    Thread.Sleep(3);
            //    clients.getClients(cachedClients);
            //    foreach (EzyClient one in cachedClients)
            //        one.processEvents();
            //}
        }
    }

    class ExHandshakeEventHandler : EzyHandshakeHandler
    {

        protected override EzyRequest getLoginRequest()
        {
            return new EzyLoginRequest("example", "dungtv", "123456");
        }
    }

    class ExLoginSuccessHandler : EzyLoginSuccessHandler
    {

        protected override void handleLoginSuccess(EzyData responseData)
        {
            client.send(new EzyAppAccessRequest("hello-world"));
        }
    }

    class ExAccessAppHandler : EzyAppAccessHandler
    {

        protected override void postHandle(EzyApp app, EzyArray data)
        {
            //app.send(Commands.ACCESS_LOBBY_ROOM);
            //this.client.send(new EzyAppExitRequest(app.getId()));
            this.client.udpConnect(2611);
            if (this.client.isEnableSSL())
            {
                app.send(
                    Commands.SECURE_CHAT,
                    EzyEntityFactory
                        .newObjectBuilder()
                        .append("who", "Young Monkeys")
                        .build(),
                    true
                );
            }
        }
    }

    class AccessLobbyResponseHandler : EzyAppDataHandler
    {

        public void handle(EzyApp app, EzyData d)
        {
            EzyObject data = (EzyObject)d;
            long currentRoomId = data.get<long>("currentRoomId");
            if (currentRoomId <= 0)
            {
                app.send(Commands.JOIN_OR_CREATE_ROOM);
            }
            else
            {
                app.send(Commands.RECONNECT);
            }
        }
    }

    class RoomInfoResponseHandler : EzyAppDataHandler
    {

        public void handle(EzyApp app, EzyData d)
        {
            EzyArray pos = EzyEntityFactory.newArrayBuilder()
                                           .append(1.0D, 2.0D, 3.0D)
                                           .build();
            app.send(Commands.SYNC_POSITION, pos);
        }
    }

    class SyncPositionHandler : EzyAppDataHandler
    {

        public void handle(EzyApp app, EzyData d)
        {
            //System.out.println("syn pos: " + data);
        }
    }

    class UdpHandshakeHandler : EzyUdpHandshakeHandler
    {

        protected override void onAuthenticated(EzyArray data)
        {
            EzyApp app = client.getZone().getApp();
            app.udpSend("udpGreet", EzyEntityFactory.newObjectBuilder()
                    .append("who", "Dzung")
                    .build());
        }

    }
}

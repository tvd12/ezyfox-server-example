package com.example.hello_world.client;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.util.EzyEntityObjects;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.EzyUTClient;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.event.EzyEventType;
import com.tvd12.ezyfoxserver.client.handler.*;
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;
import com.tvd12.ezyfoxserver.client.setup.EzyAppSetup;
import com.tvd12.ezyfoxserver.client.setup.EzySetup;
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;
import com.tvd12.ezyhttp.client.HttpClient;
import com.tvd12.ezyhttp.client.request.PostRequest;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldClientUdp {

    private final EzyClient socketClient;
    private static final String ZONE_NAME = "example";
    private static final String APP_NAME = "hello-world";

    public HelloWorldClientUdp() {
        this.socketClient = setup();
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 3005;
        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        HelloWorldClientUdp client = new HelloWorldClientUdp();
        client.connect(host, port);
        EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop();
        mainEventsLoop.start();
    }

    protected EzyClient setup() {
        EzyClientConfig clientConfig = EzyClientConfig.builder()
            .zoneName(ZONE_NAME)
            .enableSSL()
            .enableDebug()
            .build();
        EzyClients clients = EzyClients.getInstance();
        EzyClient client = new EzyUTClient(clientConfig);
        clients.addClient(client);
        EzySetup setup = client.setup();
        setup.addEventHandler(EzyEventType.CONNECTION_SUCCESS, new EzyConnectionSuccessHandler());
        setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, new EzyConnectionFailureHandler());
        setup.addDataHandler(EzyCommand.HANDSHAKE, new ExHandshakeEventHandler());
        setup.addDataHandler(EzyCommand.LOGIN, new ExLoginSuccessHandler());
        setup.addDataHandler(EzyCommand.UDP_HANDSHAKE, new ExHandshakeSuccessHandler());
        setup.addDataHandler(EzyCommand.APP_ACCESS, new ExAccessAppHandler());

        EzyAppSetup appSetup = setup.setupApp(APP_NAME);
        appSetup.addDataHandler("greet", new ChatGreetResponseHandler());
        appSetup.addDataHandler(
            "udpGreet",
            (EzyAppDataHandler<EzyData>) (app, data) ->
                System.out.println("udpGreet: " + data)
        );
        appSetup.addDataHandler("secureChat", new SecureChatResponseHandler());
        appSetup.addDataHandler(
            "chatAll",
            (EzyAppDataHandler<EzyData>) (app, data) ->
                System.out.println("chatAll: " + data)
        );
        appSetup.addDataHandler(
            "chat1",
            (EzyAppDataHandler<EzyData>) (app, data) ->
                System.out.println("chat1: " + data)
        );
        appSetup.addDataHandler(
            "chatToMe",
            (EzyAppDataHandler<EzyData>) (app, data) ->
                System.out.println("chatToMe: " + data)
        );
        appSetup.addDataHandler(
            "err",
            (EzyAppDataHandler<EzyData>) (app, data) ->
                System.out.println("error: " + data)
        );
        return client;
    }

    public void connect(String host, int port) {
        socketClient.connect(host, port);
    }

    static class ExHandshakeEventHandler extends EzyHandshakeHandler {
        @Override
        protected EzyRequest getLoginRequest() {
            final String username = "username";
            final String password = "password";
            final String accessToken = httpLogin(username, password);
            if (EzyStrings.isBlank(accessToken)) {
                return new EzyLoginRequest(ZONE_NAME, username, password);
            }
            return new EzyLoginRequest(
                ZONE_NAME,
                "",
                "",
                EzyEntityObjects.newObject("accessToken", accessToken));
        }

        private String httpLogin(String username, String password) {
            HttpClient httpClient = HttpClient.builder()
                .build();
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", username);
            requestBody.put("password", password);
            try {
                Map<String, String> response = httpClient.call(
                    new PostRequest()
                        .setURL("http://localhost:8083/api/v1/login")
                        .setEntity(requestBody)
                        .setResponseType(Map.class)
                );
                return response.get("accessToken");
            } catch (Exception e) {
                logger.info("can not login with http, error: {}", e.getMessage());
            }
            return null;
        }
    }

    static class ExLoginSuccessHandler extends EzyLoginSuccessHandler {
        @Override
        protected void handleLoginSuccess(EzyData responseData) {
            client.udpConnect(2611);
        }
    }

    static class ExHandshakeSuccessHandler extends EzyUdpHandshakeHandler {
        @Override
        protected void onAuthenticated(EzyArray data) {
            client.send(new EzyAppAccessRequest(APP_NAME));
        }
    }

    static class ExAccessAppHandler extends EzyAppAccessHandler {
        protected void postHandle(EzyApp app, EzyArray data) {
            sendGreetRequest(app);
            sendUdpGreetRequest(app);
            sendSecureChatRequest(app);
            sendOtherRequests(app);
        }

        private void sendGreetRequest(EzyApp app) {
            app.send("greet",
                EzyEntityObjects.newObject("who", "Dzung")
            );
        }

        private void sendUdpGreetRequest(EzyApp app) {
            app.send("udpGreet",
                EzyEntityObjects.newObject("who", "Dzung")
            );
        }

        private void sendSecureChatRequest(EzyApp app) {
            app.send("secureChat",
                EzyEntityObjects.newObject("who", "Dzung"),
                true
            );
        }

        private void sendOtherRequests(EzyApp app) {
            app.send("chatAll",
                EzyEntityObjects.newObject("who", "Dzung"),
                true
            );
            app.send("chatAll",
                EzyEntityObjects.newObject("who", ""),
                true
            );
            app.send("chat1",
                EzyEntityObjects.newObject("who", "Dzung"),
                true
            );
            app.send("chat1",
                EzyEntityObjects.newObject("who", ""),
                true
            );
            app.send("chat1",
                EzyEntityObjects.newObject("who", "admin"),
                true
            );
            app.send("chat2",
                EzyEntityObjects.newObject("who", "Dzung"),
                true
            );
            app.send("chatToMe",
                EzyEntityObjects.newObject("who", "Me"),
                true
            );
            app.send("chatToMe",
                EzyEntityObjects.newObject("who", ""),
                true
            );
        }

    }

    static class ChatGreetResponseHandler implements EzyAppDataHandler<EzyObject> {
        public void handle(EzyApp app, EzyObject data) {
            System.out.println("Received greet data: " + data);
        }
    }

    static class SecureChatResponseHandler implements EzyAppDataHandler<EzyObject> {
        public void handle(EzyApp app, EzyObject data) {
            System.out.println("Received secure data: " + data);
        }
    }
}

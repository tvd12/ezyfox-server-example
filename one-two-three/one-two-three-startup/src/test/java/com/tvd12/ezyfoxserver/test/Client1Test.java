package com.tvd12.ezyfoxserver.test;

import com.tvd12.ezyfox.core.constant.EzyResponseCommands;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.ApplicationStartup;
import com.tvd12.ezyfoxserver.app.constant.Commands;
import com.tvd12.ezyfoxserver.app.data.GameAnswer;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.EzyTcpClient;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.handler.EzyAppAccessHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyHandshakeHandler;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginSuccessHandler;
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;
import com.tvd12.ezyfoxserver.client.setup.EzyAppSetup;
import com.tvd12.ezyfoxserver.client.setup.EzySetup;
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;
import com.tvd12.test.util.RandomUtil;

public class Client1Test {

    public static void main(String[] args) {
        EzyClientConfig config = EzyClientConfig.builder()
            .zoneName(ApplicationStartup.ZONE_APP_NAME)
            .build();
        EzyClient client = new EzyTcpClient(config);
        setupClient(client);
        EzyClients.getInstance().addClient(client);
        client.connect("127.0.0.1", 3005);
        EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop();
        mainEventsLoop.start(5);
    }

    public static void setupClient(EzyClient client) {
        EzySetup setup = client.setup();
        setup.addDataHandler(EzyCommand.HANDSHAKE, new EzyHandshakeHandler() {

            @Override
            protected EzyRequest getLoginRequest() {
                return new EzyLoginRequest(
                    ApplicationStartup.ZONE_APP_NAME,
                    "Player1",
                    "YoungMonkey"
                );
            }
        });
        setup.addDataHandler(EzyCommand.LOGIN, new EzyLoginSuccessHandler() {
            @Override
            protected void handleLoginSuccess(EzyData responseData) {
                client.send(new EzyAppAccessRequest(ApplicationStartup.ZONE_APP_NAME));
            }
        });
        setup.addDataHandler(EzyCommand.APP_ACCESS, new EzyAppAccessHandler() {
            @Override
            protected void postHandle(EzyApp app, EzyArray data) {
                app.send(Commands.JOIN_GAME, EzyEntityFactory.EMPTY_OBJECT);
            }
        });

        EzyAppSetup appSetup = setup.setupApp(ApplicationStartup.ZONE_APP_NAME);
        appSetup.addDataHandler(EzyResponseCommands.ERROR, (app, data) -> {
            System.out.println("error: " + data);
        });
        appSetup.addDataHandler(Commands.JOIN_GAME, (app, data) -> {
            System.out.println("join game: " + data);
        });
        appSetup.addDataHandler(Commands.ANOTHER_PLAYER_JOIN_GAME, (app, data) -> {
            System.out.println("another player join game: " + data);
        });
        appSetup.addDataHandler(Commands.GAME_STARTED, (app, data) -> {
            System.out.println("new game started");
            app.send(Commands.ANSWER, EzyEntityFactory.newObjectBuilder()
                .append("answer", RandomUtil.randomEnumValue(GameAnswer.class).toString())
                .build()
            );
        });
        appSetup.addDataHandler(Commands.GAME_FINISHED, (app, data) -> {
            System.out.println("game finished: " + data);
        });
    }
}

package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.EzyWsClient;
import com.tvd12.ezyfoxserver.client.concurrent.EzyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.concurrent.EzyNettyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebSocketStressTest {

    public static void main(String[] args) {
        DefaultClientConfig clientConfig = new DefaultClientConfig();
        SocketClientSetup setup = new SocketClientSetup("websocket");
        EzyClients clients = EzyClients.getInstance();
        EzyEventLoopGroup eventLoopGroup = new EzyEventLoopGroup(16);
        EventLoopGroup nettyEventLoopGroup = new EzyNettyEventLoopGroup(16);
        new Thread(() -> {
            int clientCount = 300;
            for (int i = 0; i < clientCount; i++) {
                EzyWsClient client = new EzyWsClient(
                    clientConfig.get(i),
                    eventLoopGroup,
                    nettyEventLoopGroup
                );
                try {
                    Thread.sleep(25);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setup.setup(client, false);
                clients.addClient(client);
                client.connect("ws://127.0.0.1:2208/ws");
            }
        })
            .start();
        EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop();
        mainEventsLoop.start(5);
    }
}

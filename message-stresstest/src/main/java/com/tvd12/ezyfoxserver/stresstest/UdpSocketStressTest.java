package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.EzyTcpClient;
import com.tvd12.ezyfoxserver.client.EzyUTClient;
import com.tvd12.ezyfoxserver.client.concurrent.EzyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.concurrent.EzyNettyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UdpSocketStressTest {

    public static void main(String[] args) {
        DefaultClientConfig clientConfig = new DefaultClientConfig();
        SocketClientSetup setup = new SocketClientSetup("udp-socket");
        EzyClients clients = EzyClients.getInstance();
        EzyEventLoopGroup eventLoopGroup = new EzyEventLoopGroup(16);
        EventLoopGroup nettyEventLoopGroup = new EzyNettyEventLoopGroup(16);
        new Thread(() -> {
            int clientCount = 2000;
            for (int i = 0; i < clientCount; i++) {
                EzyTcpClient client = new EzyUTClient(
                    clientConfig.get(i),
                    eventLoopGroup,
                    nettyEventLoopGroup
                );
                try {
                    Thread.sleep(25);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setup.setup(client, true);
                clients.addClient(client);
                client.connect("127.0.0.1", 3005);
            }
        })
            .start();
        EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop();
        mainEventsLoop.start(5);
    }
}

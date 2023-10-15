package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfox.concurrent.EzyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyWsClient;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebSocketStressTest extends SocketStressTest {

    public static void main(String[] args) {
        new WebSocketStressTest().test();
    }

    @Override
    protected int clientCount() {
        return 300;
    }

    @Override
    protected int testDurationInSecond() {
        return 5 * 60;
    }

    @Override
    protected EzyClient newClient(
        EzyClientConfig config,
        EzyEventLoopGroup eventLoopGroup,
        EventLoopGroup nettyEventLoopGroup
    ) {
        return new EzyWsClient(
            config,
            eventLoopGroup,
            nettyEventLoopGroup
        );
    }

    @Override
    protected void connect(EzyClient client) {
        client.connect("ws://127.0.0.1:2208/ws");
    }
}

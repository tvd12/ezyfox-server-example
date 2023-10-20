package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfox.concurrent.EzyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyUTClient;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UdpSocketStressTest extends SocketStressTest {

    public static void main(String[] args) {
        new UdpSocketStressTest().test();
    }

    @Override
    protected int clientCount() {
        return 300;
    }

    @Override
    protected boolean useUdp() {
        return true;
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
        return new EzyUTClient(
            config,
            eventLoopGroup,
            nettyEventLoopGroup
        );
    }

    @Override
    protected void connect(EzyClient client) {
        client.connect("127.0.0.1", 3005);
    }
}

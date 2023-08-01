package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfox.concurrent.EzyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyTcpClient;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TcpSocketStressTest extends SocketStressTest {

    public static void main(String[] args) {
        new TcpSocketStressTest().test();
    }


    @Override
    protected int clientCount() {
        return 100;
    }

    @Override
    protected int testDurationInSecond() {
        return 5;
    }

    @Override
    protected EzyClient newClient(
        EzyClientConfig config,
        EzyEventLoopGroup eventLoopGroup,
        EventLoopGroup nettyEventLoopGroup
    ) {
        return new EzyTcpClient(
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

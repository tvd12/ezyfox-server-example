package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfox.concurrent.EzyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyTcpClient;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import com.tvd12.ezyfoxserver.client.constant.EzySslType;
import io.netty.channel.EventLoopGroup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CertificationSslTcpSocketStressTest extends SocketStressTest {

    public static void main(String[] args) {
        new CertificationSslTcpSocketStressTest().test();
    }

    @Override
    protected int clientCount() {
        return 100;
    }

    @Override
    protected int testDurationInSecond() {
        return 5 * 60;
    }

    @Override
    protected void decorateConfigBuilder(
        EzyClientConfig.Builder configBuilder
    ) {
        configBuilder
            .socketEnableSSL(true)
            .socketSslType(EzySslType.CERTIFICATION)
            .build();
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

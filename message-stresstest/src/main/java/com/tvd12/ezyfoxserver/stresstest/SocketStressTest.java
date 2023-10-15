package com.tvd12.ezyfoxserver.stresstest;

import com.tvd12.ezyfox.concurrent.EzyEventLoopGroup;
import com.tvd12.ezyfox.util.EzyThreads;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.EzyClients;
import com.tvd12.ezyfoxserver.client.concurrent.EzyNettyEventLoopGroup;
import com.tvd12.ezyfoxserver.client.config.EzyClientConfig;
import com.tvd12.ezyfoxserver.client.metrics.EzyMetricsRecorder;
import com.tvd12.ezyfoxserver.client.socket.EzyMainEventsLoop;
import com.tvd12.ezyfoxserver.stresstest.setup.ClientConfigFactory;
import com.tvd12.ezyfoxserver.stresstest.setup.SocketClientSetup;
import io.netty.channel.EventLoopGroup;

import static com.tvd12.ezyfox.util.EzyProcessor.processWithLogException;

public abstract class SocketStressTest {

    public void test() {
        ClientConfigFactory clientConfigFactory = new ClientConfigFactory();
        SocketClientSetup setup = new SocketClientSetup("tcp-socket");
        EzyClients clients = EzyClients.getInstance();
        EzyEventLoopGroup eventLoopGroup = new EzyEventLoopGroup(32);
        EventLoopGroup nettyEventLoopGroup = new EzyNettyEventLoopGroup(32);
        new Thread(() -> {
            for (int i = 0; i < clientCount(); i++) {
                EzyClientConfig.Builder configBuilder = clientConfigFactory
                    .newConfigBuilder(i + 1);
                decorateConfigBuilder(configBuilder);
                EzyClient client = newClient(
                    configBuilder.build(),
                    eventLoopGroup,
                    nettyEventLoopGroup
                );
                try {
                    EzyThreads.sleep(25);
                } catch (Exception e) {
                    break;
                }
                setup.setup(client, useUdp());
                clients.addClient(client);
                connect(client);
            }
        })
            .start();
        EzyMainEventsLoop mainEventsLoop = new EzyMainEventsLoop() {
            @Override
            public void start(int sleepTime) {
                long duration = testDurationInSecond() * 1000L;
                long startTestTime = System.currentTimeMillis();
                long endTestTime = startTestTime + duration;
                while (true) {
                    this.processEvents(3);
                    long currentTime = System.currentTimeMillis();
                    if (currentTime >= endTestTime) {
                        break;
                    }
                }
                EzyMetricsRecorder metricsRecorder = EzyMetricsRecorder
                    .getDefault();
                metricsRecorder.endRecording();
                processWithLogException(metricsRecorder::printMetrics);
                EzyClients.getInstance().disconnectClients();
                eventLoopGroup.shutdown();
                setup.close();
                nettyEventLoopGroup.shutdownGracefully();
            }
        };
        mainEventsLoop.start(5);
    }

    protected void decorateConfigBuilder(
        EzyClientConfig.Builder configBuilder
    ) {}

    protected abstract int clientCount();

    protected boolean useUdp() {
        return false;
    }

    protected abstract int testDurationInSecond();

    protected abstract EzyClient newClient(
        EzyClientConfig config,
        EzyEventLoopGroup eventLoopGroup,
        EventLoopGroup nettyEventLoopGroup
    );

    protected abstract void connect(EzyClient client);
}

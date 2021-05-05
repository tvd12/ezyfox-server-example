package com.tvd12.mmorpg.stresstest.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.setup.EzyAppSetup;
import com.tvd12.ezyfoxserver.client.setup.EzySetup;

public class SocketClientSetup {

	private final AtomicInteger counter = new AtomicInteger();
	private final ScheduledExecutorService executorService;
	
	public SocketClientSetup() {
		this.executorService = EzyExecutors.newScheduledThreadPool(
			5,
			"neighbours-schedule"
		);
		Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));
	}
	
	public void setup(EzyClient client) {
		int count = counter.incrementAndGet();
		EzySetup setup = client.setup();
		setup.addDataHandler(EzyCommand.HANDSHAKE, new HandshakeHandler(count));
		setup.addDataHandler(EzyCommand.LOGIN, new LoginSuccessHandler());
		setup.addDataHandler(EzyCommand.UDP_HANDSHAKE, new UdpHandshakeHandler());
		setup.addDataHandler(EzyCommand.APP_ACCESS, new AccessAppHandler(executorService));

		EzyAppSetup appSetup = setup.setupApp("mmorpg");

		appSetup.addDataHandler("p", (app, data) -> {
		});
		appSetup.addDataHandler("r", (app, data) -> {
			Statistics.getInstance().onMessageReceived(
				app.getClient().getMe().getName(),
				(EzyArray) data
			);
		});
	}
	
}

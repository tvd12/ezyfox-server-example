package com.tvd12.ezyfoxserver.stresstest;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.constant.EzyCommand;
import com.tvd12.ezyfoxserver.client.setup.EzyAppSetup;
import com.tvd12.ezyfoxserver.client.setup.EzySetup;
import com.tvd12.ezyfoxserver.stresstest.handler.AccessAppHandler;
import com.tvd12.ezyfoxserver.stresstest.handler.HandshakeHandler;
import com.tvd12.ezyfoxserver.stresstest.handler.LoginSuccessHandler;
import com.tvd12.ezyfoxserver.stresstest.handler.UdpHandshakeHandler;

public class SocketClientSetup {

	private final AtomicInteger counter = new AtomicInteger();
	private final AtomicLong messageCount = new AtomicLong();
	private final ScheduledExecutorService executorService;
	
	public SocketClientSetup(String clientType) {
		this.executorService = EzyExecutors.newScheduledThreadPool(5, clientType + "-message-schedule");
		Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));
	}
	
	public void setup(EzyClient client, boolean useUdp) {
		int count = counter.incrementAndGet();
		EzySetup setup = client.setup();
		setup.addDataHandler(EzyCommand.HANDSHAKE, new HandshakeHandler(count));
		setup.addDataHandler(EzyCommand.LOGIN, new LoginSuccessHandler(useUdp));
		setup.addDataHandler(EzyCommand.UDP_HANDSHAKE, new UdpHandshakeHandler());
		setup.addDataHandler(EzyCommand.APP_ACCESS, new AccessAppHandler(useUdp, messageCount, executorService));
		
		EzyAppSetup appSetup = setup.setupApp("hello-world");
		appSetup.addDataHandler("broadcastMessage", (app, data) -> {
//			String message = ((EzyObject)data).get("message", String.class);
//			System.out.println("tcp > server response: " + message);
		});
		appSetup.addDataHandler("udpBroadcastMessage", (app, data) -> {
//			String message = ((EzyObject)data).get("message", String.class);
//			System.out.println("udp > server response: " + message);
		});
	}
	
}

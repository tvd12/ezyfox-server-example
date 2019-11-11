package com.tvd12.ezyfoxserver.stresstest.handler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.handler.EzyAppAccessHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessAppHandler extends EzyAppAccessHandler {

	private final AtomicLong messageCount;
	private final ScheduledExecutorService executorService;
	
	@Override
	protected void postHandle(EzyApp app, EzyArray data) {
		executorService.scheduleAtFixedRate(() -> sendMessage(app), 3, 5, TimeUnit.SECONDS);
//		this.client.send(new EzyAppExitRequest(app.getId()));
	}
	
	private void sendMessage(EzyApp app) {
		app.send("broadcastMessage", newMessageData());
	}
	
	private EzyObject newMessageData() {
		return EzyEntityFactory.newObjectBuilder()
			.append("message", "Message#" + messageCount.incrementAndGet())
			.build();
	}
	
}

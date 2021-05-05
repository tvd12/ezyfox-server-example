package com.tvd12.mmorpg.stresstest.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfoxserver.client.entity.EzyApp;
import com.tvd12.ezyfoxserver.client.handler.EzyAppAccessHandler;

import lombok.AllArgsConstructor;

import static com.tvd12.mmorpg.stresstest.client.Statistics.*;

@AllArgsConstructor
public class AccessAppHandler extends EzyAppAccessHandler {

	private final ScheduledExecutorService executorService;
	
	@Override
	protected void postHandle(EzyApp app, EzyArray data) {
		int period = MILLIS_PER_SECOND / EXPECTED_RESPONSES_PER_SECOND;
		executorService.scheduleAtFixedRate(() -> sendMessage(app), 3, period, TimeUnit.MILLISECONDS);
	}
	
	private void sendMessage(EzyApp app) {
		try {
			app.udpSend("r", EzyEntityFactory.EMPTY_ARRAY);
			Statistics.getInstance().onMessageSent(app.getClient().getMe().getName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.tvd12.mmorpg.stresstest.client;

import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginSuccessHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginSuccessHandler extends EzyLoginSuccessHandler {

	@Override
	protected void handleLoginSuccess(EzyData responseData) {
		client.udpConnect(2611);
	}
}

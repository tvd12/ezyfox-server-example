package com.tvd12.ezyfoxserver.stresstest.handler;

import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginSuccessHandler;
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginSuccessHandler extends EzyLoginSuccessHandler {

	protected final boolean connectUdp;
	
	@Override
	protected void handleLoginSuccess(EzyData responseData) {
		if(connectUdp) {
			client.udpConnect(2611);
		}
		else {
			client.send(new EzyAppAccessRequest("hello-world"));
		}
	}
}

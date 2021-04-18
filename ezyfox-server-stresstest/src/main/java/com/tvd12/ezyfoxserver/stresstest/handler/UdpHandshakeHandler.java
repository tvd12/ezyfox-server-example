package com.tvd12.ezyfoxserver.stresstest.handler;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.client.handler.EzyUdpHandshakeHandler;
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest;

public class UdpHandshakeHandler extends EzyUdpHandshakeHandler {

	@Override
	protected void onAuthenticated(EzyArray data) {
		client.send(new EzyAppAccessRequest("hello-world"));
	}
	
}

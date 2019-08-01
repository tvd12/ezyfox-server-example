package com.tvd12.ezyfoxserver.stresstest.handler;

import com.tvd12.ezyfoxserver.client.handler.EzyHandshakeHandler;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.client.request.EzyRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HandshakeHandler extends EzyHandshakeHandler {

	private final int count;
	
	@Override
	protected EzyRequest getLoginRequest() {
		return new EzyLoginRequest("example", "Mac13#" + count, "123456");
	}

}

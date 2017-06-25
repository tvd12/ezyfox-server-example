package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfoxserver.client.cmd.EzySendRequest;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.controller.EzyLoginController;
import com.tvd12.ezyfoxserver.client.entity.EzyClientSession;
import com.tvd12.ezyfoxserver.client.request.EzyAccessAppRequest;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyObject;

public class ChatLoginController extends EzyLoginController {

	@Override
	public void handle(EzyClientContext ctx, EzyClientSession session, EzyArray data) {
		super.handle(ctx, session, data);
		sendAccessAppsRequest(ctx);
	}
	
	protected void sendAccessAppsRequest(EzyClientContext ctx) {
		ctx.get(EzySendRequest.class)
			.sender(ctx.getMe())
			.request(newAccessAppRequest())
			.execute();
	}
	
	protected EzyAccessAppRequest newAccessAppRequest() {
		return EzyAccessAppRequest.builder()
				.appName("ezyfox-chat")
				.data(newAccessAppData())
				.build();
	}
	
	protected EzyObject newAccessAppData() {
		return newObjectBuilder().build();
	}
}

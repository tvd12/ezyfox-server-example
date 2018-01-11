package com.tvd12.ezyfoxserver.simplechat.controller;

import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyObject;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.event.EzyUserRequestAppEvent;

public class EzyChatUserRequestController 
		extends EzyAbstractAppEventController<EzyUserRequestAppEvent> {

	@Override
	public void handle(EzyAppContext context, EzyUserRequestAppEvent event) {
		EzyArray data = event.getData();
		String cmd = data.get(0, String.class);
		EzyUser user = event.getUser();
		EzyObject params = data.get(1, EzyObject.class);
		String message = params.get("message", String.class);
		getLogger().info("received message: {} from user: {}", message, user.getName());
		context.get(EzyAppResponse.class)
			.command(cmd)
			.params(newObjectBuilder()
						.append("message", message)
						.append("sender", user.getName()))
			.users(context.getApp().getUserManager().getUserList())
			.execute();
	}
	
}

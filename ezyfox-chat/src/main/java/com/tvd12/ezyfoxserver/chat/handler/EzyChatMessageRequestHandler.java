package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.context.EzyContext;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyUser;

public class EzyChatMessageRequestHandler extends EzyAbstractClientRequestHandler {

	@Override
	public void handle(EzyContext context, EzyUser user, EzyArray params) {
		String message = params.get(0, String.class);
		EzyArray response = newArrayBuilder().append(message + ", too!").build();
		getLogger().info("user {} chat {}", user.getName(), message);
		context.get(EzyAppResponse.class).user(user).command("1").params(response).execute();
	}

}

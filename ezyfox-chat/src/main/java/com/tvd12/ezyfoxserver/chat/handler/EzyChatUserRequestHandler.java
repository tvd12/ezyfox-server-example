package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.context.EzyContext;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyUser;

public class EzyChatUserRequestHandler extends EzyAbstractClientRequestHandler {

	@Override
	public void handle(EzyContext context, EzyUser user, EzyArray params) {
		String message = params.get(0, String.class);
		String receiver = params.get(1, String.class);
		EzyArray response = newArrayBuilder()
				.append(message)
				.append(receiver)
				.append(user.getName())
				.build();
		getLogger().info("user {} chat {}", user.getName(), message);
		context.get(EzyAppResponse.class)
			.command("2")
			.params(response)
			.recipient(user)
			.recipient(receiver)
			.execute();
	}

}

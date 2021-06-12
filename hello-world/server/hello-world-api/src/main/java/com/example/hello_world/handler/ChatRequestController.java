package com.example.hello_world.handler;

import static com.example.hello_world.constant.Commands.SECURE_CHAT;

import com.example.hello_world.common.Greeting;
import com.example.hello_world.request.ChatRequest;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;

@EzyRequestController
public class ChatRequestController {

	@EzyAutoBind
	private Greeting greeting;
	@EzyAutoBind
	private EzyResponseFactory responseFactory;
	
	@EzyDoHandle(SECURE_CHAT)
	public void secureChat(EzyUser user, ChatRequest request) {
		responseFactory.newObjectResponse()
			.encrypted()
			.command(SECURE_CHAT)
			.param("secure-message", greeting.greet(request.getWho()))
			.user(user)
			.execute();
	}
	
}

package com.example.hello_world.handler;

import com.example.hello_world.common.Greeting;
import com.example.hello_world.constant.Commands;
import com.example.hello_world.exception.BadWhoRequestException;
import com.example.hello_world.exception.InvalidChatRequestException;
import com.example.hello_world.request.ChatRequest;
import com.example.hello_world.response.ChatResponse;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyContext;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.event.EzyUserSessionEvent;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;

@EzyRequestController
public class ChatRequestController extends EzyLoggable {

	@EzyAutoBind
	private Greeting greeting;
	@EzyAutoBind
	private EzyResponseFactory responseFactory;
	
	@EzyDoHandle(Commands.SECURE_CHAT)
	public void secureChat(EzyUser user, ChatRequest request) {
		responseFactory.newObjectResponse()
			.encrypted()
			.command(Commands.SECURE_CHAT)
			.param("secure-message", greeting.greet(request.getWho()))
			.user(user)
			.execute();
	}
	
	@EzyDoHandle(Commands.CHAT_ALL)
	public void chatAll(
			EzyAppContext context,
			EzyUserSessionEvent event,
			EzyUser user,
			EzySession session,
			ChatRequest request) {
	    if (EzyStrings.isBlank(request.getWho())) {
	        throw new InvalidChatRequestException("request is blank");
	    }
		responseFactory.newObjectResponse()
			.command(Commands.CHAT_ALL)
			.param("message", greeting.greet(request.getWho()))
			.users(context.getApp().getUserManager().getUserList())
			.execute();
	}
	
	@EzyDoHandle(Commands.CHAT_TO_ME)
    public ChatResponse chatToMe(ChatRequest request) {
	    if (EzyStrings.isBlank(request.getWho())) {
	        throw new EzyBadRequestException(400, "badRequest");
	    }
        return new ChatResponse(greeting.greet(request.getWho()));
    }
	
	@EzyDoHandle(Commands.CHAT_1)
	public void chatFirst(
			EzyContext context,
			EzyUser user,
			ChatRequest request) {
	    if (EzyStrings.isBlank(request.getWho())) {
            throw new IllegalArgumentException("request is blank");
        }
        else if (request.getWho().equals("admin")) {
            throw new BadWhoRequestException("you can not greet admin");
        }
		responseFactory.newObjectResponse()
			.command(Commands.CHAT_1)
			.param("message", greeting.greet(request.getWho()))
			.user(user)
			.execute();
	}
	
	@EzyDoHandle(Commands.CHAT_2)
	public void chatSecond() {
		logger.info("some one call chat");
	}
}

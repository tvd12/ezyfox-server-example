package com.example.simple_chat.handler;

import static com.example.simple_chat.constant.Commands.GREET;

import com.example.simple_chat.common.Greeting;
import com.example.simple_chat.handler.GreetRequestHandler.GreetRequest;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.event.EzyUserSessionEvent;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.ezyfoxserver.support.handler.EzyUserRequestHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Setter
@EzySingleton
@EzyClientRequestListener(GREET)
public class GreetRequestHandler 
		implements EzyUserRequestHandler<EzyAppContext, GreetRequest> {

	private String who;
	
	@EzyAutoBind
	private Greeting greeting;
	
	@EzyAutoBind
	private EzyResponseFactory appResponseFactory;
	
	@Override
	public void handle(
			EzyAppContext context, 
			EzyUserSessionEvent event, GreetRequest data) {
		appResponseFactory.newObjectResponse()
			.command(GREET)
			.data(new GreetResponse(greeting.greet(data.getWho())))
			.param("message", greeting.greet(who))
			.session(event.getSession())
			.execute();
	}
	
	@Data
	@EzyObjectBinding(write = false)
	public static class GreetRequest {
		private String who;
	}
	
	@Data
	@AllArgsConstructor
	@EzyObjectBinding(read = false)
	public static class GreetResponse {
		private String message;
	}
	
}

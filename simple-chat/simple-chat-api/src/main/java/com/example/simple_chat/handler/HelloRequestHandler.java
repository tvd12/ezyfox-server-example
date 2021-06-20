package com.example.simple_chat.handler;

import static com.example.simple_chat.constant.Commands.HELLO;

import com.example.simple_chat.common.Greeting;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyRequestListener;
import com.tvd12.ezyfox.function.EzyHandler;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.context.EzyAppContextAware;
import com.tvd12.ezyfoxserver.entity.EzySession;
import com.tvd12.ezyfoxserver.entity.EzySessionAware;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.entity.EzyUserAware;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyObjectBinding // @EzyArrayBinding
@EzyRequestListener(HELLO)
public class HelloRequestHandler 
	extends EzyLoggable
	implements 
		EzyHandler,
		EzyAppContextAware, 
		EzySessionAware, 
		EzyUserAware {

	@EzyAutoBind
	protected Greeting greeting;
	
	@EzyAutoBind
	protected EzyResponseFactory appResponseFactory;
	
	protected EzyUser user;
	protected EzySession session;
	protected EzyAppContext appContext;
	
	protected String who;
	
	@Override
	public void handle() {
		appResponseFactory.newObjectResponse()
			.command(HELLO)
			.param("hello", greeting.greet(who))
			.session(session)
			.execute();
	}
}

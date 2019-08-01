package com.example.hello_world.handler;

import static com.example.hello_world.constant.Commands.BROADCAST_MESSAGE;

import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.binding.EzyDataBinding;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyClientRequestListener;
import com.tvd12.ezyfox.core.exception.EzyBadRequestException;
import com.tvd12.ezyfoxserver.EzyApplication;
import com.tvd12.ezyfoxserver.wrapper.EzyAppUserManager;

import lombok.Setter;

@Setter
@EzyPrototype
@EzyObjectBinding(write = false)
@EzyClientRequestListener(BROADCAST_MESSAGE)
public class BroacastMessageHandler 
		extends ClientRequestHandler 
		implements EzyDataBinding {

	private String message;
	
	@Override
	protected void execute() throws EzyBadRequestException {
		EzyApplication app = appContext.getApp();
		EzyAppUserManager userManager = app.getUserManager();
		responseFactory.newObjectResponse()
			.command(BROADCAST_MESSAGE)
			.param("message", message)
			.users(userManager.getUserList())
			.execute();
	}
	
}

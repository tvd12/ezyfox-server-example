package com.example.hello_world.controller;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.SERVER_READY;

import com.example.hello_world.config.AppConfig;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

@EzyEventHandler(SERVER_READY) // refer EzyEventType
public class ServerReadyController 
		extends EzyAbstractAppEventController<EzyServerReadyEvent> {

	@EzyAutoBind
	private AppConfig appConfig;
	
	@Override
	public void handle(EzyAppContext ctx, EzyServerReadyEvent event) {
		logger.info("hello-world app: fire custom app ready, node name: {}", appConfig.getNodeName());
	}
	
}

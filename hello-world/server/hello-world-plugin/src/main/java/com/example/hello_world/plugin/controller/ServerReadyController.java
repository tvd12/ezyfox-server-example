package com.example.hello_world.plugin.controller;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.SERVER_READY;

import com.example.hello_world.plugin.config.PluginConfig;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

@EzyEventHandler(SERVER_READY)
public class ServerReadyController 
		extends EzyAbstractPluginEventController<EzyServerReadyEvent> {

	@EzyAutoBind
	private PluginConfig pluginConfig;
	
	@Override
	public void handle(EzyPluginContext ctx, EzyServerReadyEvent event) {
		logger.info("hello-world plugin: fire custom plugin ready, config: {}", pluginConfig);
	}
	
}
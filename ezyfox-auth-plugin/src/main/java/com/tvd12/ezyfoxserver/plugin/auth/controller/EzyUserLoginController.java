package com.tvd12.ezyfoxserver.plugin.auth.controller;

import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;

public class EzyUserLoginController 
		extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		getLogger().info("handle user {} login", event.getUsername());
	}
	
}

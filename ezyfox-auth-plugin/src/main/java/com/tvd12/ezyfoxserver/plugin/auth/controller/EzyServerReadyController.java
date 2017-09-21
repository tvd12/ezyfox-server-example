package com.tvd12.ezyfoxserver.plugin.auth.controller;

import com.tvd12.ezyfoxserver.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

@EzySingleton(properties = {
		@EzyKeyValue(key = "type", value = "EVENT_HANDLER"),
		@EzyKeyValue(key = "name", value = "SERVER_READY")
})
public class EzyServerReadyController 
		extends EzyAbstractPluginEventController<EzyServerReadyEvent> {

	@Override
	public void handle(EzyPluginContext ctx, EzyServerReadyEvent event) {
		getLogger().info("auth plugin: fire custom app ready");
	}
	
}
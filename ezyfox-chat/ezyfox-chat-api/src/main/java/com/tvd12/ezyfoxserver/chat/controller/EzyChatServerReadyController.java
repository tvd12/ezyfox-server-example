package com.tvd12.ezyfoxserver.chat.controller;

import com.tvd12.ezyfoxserver.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;

@EzySingleton(properties = {
		@EzyKeyValue(key = "type", value = "EVENT_HANDLER"),
		@EzyKeyValue(key = "name", value = "SERVER_READY")
})
public class EzyChatServerReadyController 
		extends EzyAbstractAppEventController<EzyServerReadyEvent> {

	@Override
	public void handle(EzyAppContext ctx, EzyServerReadyEvent event) {
		getLogger().info("chat app: fire custom app ready");
	}
	
}

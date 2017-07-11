package com.tvd12.ezyfoxserver.chat.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfoxserver.chat.handler.EzyChatSystemRequestHandler;
import com.tvd12.ezyfoxserver.chat.handler.EzyChatUserRequestHandler;
import com.tvd12.ezyfoxserver.chat.handler.EzyClientRequestHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.event.EzyUserRequestAppEvent;

public class EzyUserRequestController 
		extends EzyAbstractAppEventController<EzyUserRequestAppEvent> {

	private Map<String, EzyClientRequestHandler> handlers = defaultHandlers();
	
	@Override
	public void handle(EzyAppContext context, EzyUserRequestAppEvent event) {
		EzyUser user = event.getUser();
		EzyArray data = event.getData();
		String cmd = data.get(0, String.class);
		EzyArray params = data.get(1, EzyArray.class);
		handlers.get(cmd).handle(context, user, params);
	}
	
	private Map<String, EzyClientRequestHandler> defaultHandlers() {
		Map<String, EzyClientRequestHandler> handlers = new ConcurrentHashMap<>();
		addHandler(handlers);
		return handlers;
	}
	
	private void addHandler(Map<String, EzyClientRequestHandler> handlers) {
		handlers.put("1", new EzyChatSystemRequestHandler());
		handlers.put("2", new EzyChatUserRequestHandler());
	}
	
}

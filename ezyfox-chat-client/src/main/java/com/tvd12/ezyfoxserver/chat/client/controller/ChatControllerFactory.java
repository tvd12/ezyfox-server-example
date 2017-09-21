package com.tvd12.ezyfoxserver.chat.client.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;

public class ChatControllerFactory {

	private Map<ChatEventType, Supplier<ChatController>> controllers = controllers();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends ChatController> T newController(ChatEventType type) {
		ChatController ctr = controllers.get(type).get();
		ChatAbstractController abs = (ChatAbstractController)ctr;
		return (T)abs;
	}
	
	private Map<ChatEventType, Supplier<ChatController>> controllers() {
		Map<ChatEventType, Supplier<ChatController>> answer = new ConcurrentHashMap<>();
		answer.put(ChatEventType.START, () -> new ChatStartController());
		answer.put(ChatEventType.CONNECT, () -> new ChatConnectionController());
		answer.put(ChatEventType.LOGIN, () -> new ChatLoginController());
		answer.put(ChatEventType.CHAT, () -> new ChatAllController());
		//answer.put(ChatEventType.SEARCH, () -> new ChatSearchController());
		return answer;
	}
}

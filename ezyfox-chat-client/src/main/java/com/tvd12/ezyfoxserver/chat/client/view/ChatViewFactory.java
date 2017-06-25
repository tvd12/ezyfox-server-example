package com.tvd12.ezyfoxserver.chat.client.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;

/**
 * Created by tavandung12 on 6/22/17.
 */
public class ChatViewFactory {

	private Map<ChatEventType, Map<String, ChatView>> viewss;
    
    public ChatViewFactory() {
    	this.viewss = defaultViewss();
    }

    @SuppressWarnings({"unchecked"})
	public <T extends ChatView> T getView(ChatEventType chatEventType, String modelResult) {
        return (T)viewss.get(chatEventType).get(modelResult);
    }

	private Map<ChatEventType, Map<String, ChatView>> defaultViewss() {
        Map<ChatEventType, Map<String, ChatView>> answer = new ConcurrentHashMap<>();
        Map<String, ChatView> connectionViews = new ConcurrentHashMap<>();
        connectionViews.put(ChatModel.SUCCESS, new ChatConnectionView());
        answer.put(ChatEventType.CONNECT, connectionViews);
        
        Map<String, ChatView> connectViews = new ConcurrentHashMap<>();
        connectViews.put(ChatModel.SUCCESS, new ChatLoginView());
        answer.put(ChatEventType.LOGIN, connectViews);
        
        Map<String, ChatView> loginViews = new ConcurrentHashMap<>();
        loginViews.put(ChatModel.SUCCESS, new ChatAllView());
        answer.put(ChatEventType.CHAT, loginViews);
        
        return answer;
    }

}

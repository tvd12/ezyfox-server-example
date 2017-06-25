package com.tvd12.ezyfoxserver.chat.client.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;

/**
 * Created by tavandung12 on 6/22/17.
 */
public class ChatModelFactory {

    private final Map<ChatEventType, Supplier<ChatModel>> models = defaultModels();
    
    @SuppressWarnings("unchecked")
	public <T extends ChatModel> T newModel(ChatEventType type) {
        return (T)models.get(type).get();
    }

    private  Map<ChatEventType, Supplier<ChatModel>> defaultModels() {
        Map<ChatEventType, Supplier<ChatModel>> answer = new ConcurrentHashMap<>();
        answer.put(ChatEventType.START, () -> new ChatStartModel());
        answer.put(ChatEventType.CONNECT, () -> new ChatConnectionModel());
        answer.put(ChatEventType.LOGIN, () -> new ChatLoginModel());
        answer.put(ChatEventType.CHAT, () -> new ChatAllModel());
        return answer;
    }

}

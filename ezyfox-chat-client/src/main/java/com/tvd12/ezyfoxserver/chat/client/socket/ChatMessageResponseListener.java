package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.data.ChatMessage;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.chat.client.view.ChatAllView;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;
import com.tvd12.ezyfoxserver.client.listener.EzyClientAppResponseListener;

import javafx.application.Platform;

import java.util.Date;

/**
 * Created by tavandung12 on 6/24/17.
 */
public class ChatMessageResponseListener implements EzyClientAppResponseListener<EzyArray> {
	@Override
    public void execute(EzyClientAppContext ezyClientAppContext, EzyArray array) {
		System.out.println("Message Response Listener");
        String messageContent = array.get(0, String.class);
        String messageReceiver = array.get(1,String.class);
        String messageSender = array.get(2, String.class);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(messageSender);
        chatMessage.setMessage(messageContent);
        chatMessage.setReceiver(messageReceiver);
        chatMessage.setReceiveDate(new Date());
        Platform.runLater(() -> getView().update("2", chatMessage));
    }

    protected ChatAllView getView() {
        return ChatSingleton.getInstance().getViewFactory().getView(ChatEventType.CHAT, ChatModel.SUCCESS);
    }
}

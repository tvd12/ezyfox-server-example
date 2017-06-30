package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.data.ChatMessage;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.chat.client.view.ChatAllView;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;
import com.tvd12.ezyfoxserver.client.listener.EzyClientAppResponseListener;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import javafx.application.Platform;

import java.util.Date;

/**
 * Created by tavandung12 on 6/24/17.
 */
public class ChatMessageResponseListener implements EzyClientAppResponseListener<EzyArray> {

	@Override
    public void execute(EzyClientAppContext ezyClientAppContext, EzyArray array) {
        String messageContent = array.get(0, String.class);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("me");
        chatMessage.setMessage(messageContent);
        chatMessage.setReceiveDate(new Date());
        Platform.runLater(() -> getView().update("1", chatMessage));
    }

    protected ChatAllView getView() {
        return ChatSingleton.getInstance().getViewFactory().getView(ChatEventType.CHAT, ChatModel.SUCCESS);
    }
}

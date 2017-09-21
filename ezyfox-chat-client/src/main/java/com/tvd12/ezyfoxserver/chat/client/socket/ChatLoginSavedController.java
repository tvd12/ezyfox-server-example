package com.tvd12.ezyfoxserver.chat.client.socket;

import java.util.Date;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.data.ChatMessage;
import com.tvd12.ezyfoxserver.chat.client.data.ChatUser;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.chat.client.view.ChatAllView;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;
import com.tvd12.ezyfoxserver.client.listener.EzyClientAppResponseListener;
import com.tvd12.ezyfoxserver.entity.EzyArray;

import javafx.application.Platform;

public class ChatLoginSavedController implements EzyClientAppResponseListener<EzyArray> {

	@Override
	public void execute(EzyClientAppContext ctx, EzyArray array) {
		System.out.println("Login Response Listener");
        String userName = array.get(0, String.class);
        String passWord = array.get(1,String.class);
        ChatUser user = new ChatUser();
        user.setUserName(userName);
        user.setPassWord(passWord);
        Platform.runLater(() -> getView());
		
	}
	protected ChatLoginSavedController getView() {
        return ChatSingleton.getInstance().getViewFactory().getView(ChatEventType.LOGIN, ChatModel.SUCCESS);
    }
}

package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.data.ChatUser;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.chat.client.view.ChatAllView;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;
import com.tvd12.ezyfoxserver.client.listener.EzyClientAppResponseListener;

import javafx.application.Platform;

public class ChatSelectReceiverListener implements EzyClientAppResponseListener<EzyArray> {

	@Override
	public void execute(EzyClientAppContext ctx, EzyArray array) {
		String userName = array.get(0,String.class);
		ChatUser userOnline = new ChatUser();
		userOnline.setUserName(userName);
		userOnline.setOnlineFlag(true);
		Platform.runLater(()->getView().update("select_receiver",userOnline) );
	}
	protected ChatAllView getView() {
		return ChatSingleton.getInstance()
				.getViewFactory()
				.getView(ChatEventType.CHAT, ChatModel.SUCCESS);
	}

}

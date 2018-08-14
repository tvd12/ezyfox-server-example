package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.client.context.EzyClientAppContext;
import com.tvd12.ezyfoxserver.client.controller.EzyClientAppController;
import com.tvd12.ezyfoxserver.client.entity.EzySimpleClientUser;

import javafx.application.Platform;

public class ChatAcessAppSuccessController implements EzyClientAppController<EzyArray> {

	@Override
	public void handle(EzyClientAppContext ctx, EzySimpleClientUser rev, EzyArray data) {
		Platform.runLater(this::showChatAllView);
	}
	
	protected void showChatAllView() {
		ChatSingleton.getInstance()
			.getViewFactory()
			.getView(ChatEventType.CHAT, ChatModel.SUCCESS)
			.show();
	}
}

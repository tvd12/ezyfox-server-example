package com.tvd12.ezyfoxserver.chat.client.socket;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.chat.client.view.ChatView;
import com.tvd12.ezyfoxserver.client.constants.EzyClientCommand;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.controller.EzyLoginErrorController;
import com.tvd12.ezyfoxserver.client.entity.EzyClientSession;

import javafx.application.Platform;

public class ChatLoginErrorController extends EzyLoginErrorController { 
	
	@Override
	public void handle(EzyClientContext ctx, EzyClientSession rev, EzyArray data) {
		super.handle(ctx, rev, data);
		Platform.runLater(() -> updateView(data));
	}
	
	protected void updateView(EzyArray data) {
		getView().update(EzyClientCommand.LOGIN_ERROR, data);
	}
	
	protected ChatView getView() {
		return ChatSingleton.getInstance()
				.getViewFactory()
				.getView(ChatEventType.LOGIN, ChatModel.SUCCESS);
	}
	
}

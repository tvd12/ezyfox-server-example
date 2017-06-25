package com.tvd12.ezyfoxserver.chat.client.socket;

import javafx.application.Platform;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.controller.EzyHandShakeController;
import com.tvd12.ezyfoxserver.client.entity.EzyClientSession;
import com.tvd12.ezyfoxserver.entity.EzyArray;

import lombok.AllArgsConstructor;

@SuppressWarnings("restriction")
@AllArgsConstructor
public class ChatHandShakeController extends EzyHandShakeController {

	@Override
	public void handle(EzyClientContext ctx, EzyClientSession session, EzyArray data) {
		super.handle(ctx, session, data);
		getLogger().info("will show login view");
		Platform.runLater(() -> showLoginView());
	}
	
	protected void showLoginView() {
		getLogger().info("show login view");
		ChatSingleton.getInstance()
			.getViewFactory()
			.getView(ChatEventType.LOGIN, ChatModel.SUCCESS).show();
	}
	
}

package com.tvd12.ezyfoxserver.chat.client.controller;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.chat.client.model.ChatStartModel;

import javafx.event.ActionEvent;

/**
 * Created by tavandung12 on 6/22/17.
 */
public class ChatStartController extends ChatAbstractController<ChatStartModel> {
	
	@Override
	protected void controlModelResult(ActionEvent event, ChatStartModel model, String result) {
		getViewFactory().getView(ChatEventType.CONNECT, ChatModel.SUCCESS).show();
	}
	
	@Override
	protected ChatEventType getEventType(ActionEvent event) {
		return ChatEventType.START;
	}
	
}

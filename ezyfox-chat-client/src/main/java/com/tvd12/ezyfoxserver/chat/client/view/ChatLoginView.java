/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tvd12.ezyfoxserver.chat.client.view;

import java.util.Map;

import com.tvd12.ezyfox.function.EzyApply;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatViewPath;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatController;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatLoginController;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;
import com.tvd12.ezyfoxserver.client.constants.EzyClientCommand;

import javafx.scene.control.Label;

/**
 *
 * @author MyPC
 */
public class ChatLoginView extends ChatAbstractView {

	@Override
	protected void beforeShow() {
		getView(ChatEventType.CONNECT, ChatModel.SUCCESS).hide();
	}
	
	@Override
	protected String getViewPath() {
		return ChatViewPath.LOGIN;
	}
	
	@Override
	protected ChatController getController() {
		return new ChatLoginController();
	}
	
	@Override
	protected void addUpdaters(Map<Object, EzyApply<Object>> updaters) {
		System.out.println("Chat login View");
		updaters.put(EzyClientCommand.LOGIN, (data) ->  {
			getMessageLabel().setText("");
		});
		updaters.put(EzyClientCommand.LOGIN_ERROR, (data) -> {
			getMessageLabel().setText("Login error: " + data.toString());
		});
	}
	
	protected Label getMessageLabel() {
		return getViewItem("messageLabel");
	}
}

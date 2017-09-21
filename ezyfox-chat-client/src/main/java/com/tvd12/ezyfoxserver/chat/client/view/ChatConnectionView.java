/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tvd12.ezyfoxserver.chat.client.view;

import java.util.Map;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatViewPath;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatController;
import com.tvd12.ezyfoxserver.client.constants.EzyClientCommand;
import com.tvd12.ezyfoxserver.function.EzyApply;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatConnectionController;
import javafx.scene.control.Label;

/**
 *
 * @author MyPC
 */
public class ChatConnectionView extends ChatAbstractView {
	
	@Override
	protected String getViewPath() {
		return ChatViewPath.CONNECTION;
	}
	
	@Override
	protected ChatController getController() {
		return new ChatConnectionController();
	}

	@Override
	protected void addUpdaters(Map<Object, EzyApply<Object>> updaters) {
		updaters.put(EzyClientCommand.CONNECT_SUCCESS, (data) -> {
			getMessageLabel().setText("");
		});
		updaters.put(EzyClientCommand.CONNECT_FAILURE, (data) -> {
			getMessageLabel().setText("Connection failure: " + data.toString());
		});
	}

	protected Label getMessageLabel() {
		return getViewItem("messageLabel");
	}
}

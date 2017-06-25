/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tvd12.ezyfoxserver.chat.client.view;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatViewPath;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatController;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatLoginController;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;

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
}

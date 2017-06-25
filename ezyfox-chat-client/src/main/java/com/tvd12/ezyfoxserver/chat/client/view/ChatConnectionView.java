/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tvd12.ezyfoxserver.chat.client.view;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatViewPath;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatController;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatConnectionController;

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
	
}

package com.tvd12.ezyfoxserver.chat.client;

import com.tvd12.ezyfoxserver.chat.client.controller.ChatControllerFactory;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModelFactory;
import com.tvd12.ezyfoxserver.chat.client.socket.*;
import com.tvd12.ezyfoxserver.chat.client.view.ChatViewFactory;
import com.tvd12.ezyfoxserver.client.EzyClient;
import com.tvd12.ezyfoxserver.client.constants.EzyClientCommand;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.context.EzyClientContextBuilder;

import lombok.Getter;

@Getter
public class ChatSingleton {

	private ChatModelFactory modelFactory;
	private ChatViewFactory viewFactory;
	private ChatControllerFactory controllerFactory;

	private EzyClientContext clientContext;
	
	private static final ChatSingleton INSTANCE = new ChatSingleton();
	
	private ChatSingleton() {
		this.modelFactory = new ChatModelFactory();
		this.viewFactory = new ChatViewFactory();
		this.controllerFactory = new ChatControllerFactory();
		this.clientContext = newClientContext();
	}
	
	public static ChatSingleton getInstance() {
		return INSTANCE;
	}
	
	private EzyClientContext newClientContext() {
		return EzyClientContextBuilder.newInstance()
				.setupClient(this::setupClient)
				.build();
	}
	
	private void setupClient(EzyClient client) {
		client.addController(EzyClientCommand.HANDSHAKE, new ChatHandShakeController());
		client.addController(EzyClientCommand.LOGIN, new ChatLoginController());
		client.addController(EzyClientCommand.LOGIN_ERROR, new ChatLoginErrorController());
		client.addController(EzyClientCommand.DISCONNECT, new ChatDisconnectController());
		client.addController(EzyClientCommand.CONNECT_FAILURE, new ChatConnectFailureController());
		client.addAppController(EzyClientCommand.ACESS_APP_SUCCESS, new ChatAcessAppSuccessController());
		client.addClientAppResponseListener("1", new ChatMessageResponseListener());
	}
	
}

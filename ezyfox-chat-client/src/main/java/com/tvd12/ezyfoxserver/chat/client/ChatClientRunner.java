package com.tvd12.ezyfoxserver.chat.client;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;

import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class ChatClientRunner extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		ChatSingleton.getInstance()
			.getControllerFactory()
			.newController(ChatEventType.START).control(null);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}

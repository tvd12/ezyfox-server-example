package com.tvd12.ezyfoxserver.chat.client.controller;

import com.tvd12.ezyfoxserver.chat.client.ChatApplication;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatLoginModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tavandung12 on 6/22/17.
 */
@Setter
@Getter
@SuppressWarnings("restriction")
public class ChatLoginController extends ChatAbstractController<ChatLoginModel> {

	@FXML
	private TextField usernameTextField;

	@FXML
	private TextField passwordTextField;

	public void onLoginActive(ActionEvent event) {
		control(event);
	}
	
	public void onExitActive(ActionEvent event) {
		ChatApplication.exit(0);
	}
	
	@Override
	protected ChatLoginModel getModel(ActionEvent event) {
		ChatLoginModel model = super.getModel(event);
		model.setUsername(usernameTextField.getText());
		model.setPassword(usernameTextField.getText());
		return model;
	}

	@Override
	protected ChatEventType getEventType(ActionEvent event) {
		return ChatEventType.LOGIN;
	}
	
}

package com.tvd12.ezyfoxserver.chat.client.controller;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.model.ChatAllModel;

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
public class ChatAllController extends ChatAbstractController<ChatAllModel> {

	@FXML
	private TextField messageTextField; 

	public void sendMessage(ActionEvent event) {
		control(event);
	}
	
	@Override
	protected void controlModelResult(ActionEvent event, ChatAllModel model, String result) {
	}

	@Override
	protected ChatAllModel getModel(ActionEvent event) {
		ChatAllModel model = super.getModel(event);
		model.setMessage(messageTextField.getText());
		return model;
	}

	@Override
	protected ChatEventType getEventType(ActionEvent event) {
		return ChatEventType.CHAT;
	}
	
}

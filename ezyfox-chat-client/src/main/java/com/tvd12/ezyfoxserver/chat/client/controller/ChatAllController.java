package com.tvd12.ezyfoxserver.chat.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatActionType;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.data.ChatUser;
import com.tvd12.ezyfoxserver.chat.client.model.ChatAllModel;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tavandung12 on 6/22/17.
 */
@Setter
@Getter
public class ChatAllController extends ChatAbstractController<ChatAllModel> {

	@FXML
	private Button sendButton;
	@FXML
	private TextField messageTextField;
	@FXML
	private TextField searchUserTextfield;
	@FXML 
	private Label receiverName;
	@FXML
	private ListView<ChatUser> userOnlineList;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void sendMessage(Event event) {
		control(event);
	}
	
	public void sendUserName(Event event) {
		control(event);
	}
	public void selectReceiver(Event event) {
		control(event);
	}
	
	@Override
	protected ChatAllModel getModel(Event event) {
		Object source = event.getSource();
		getLogger().debug("received data from source {}", source);
		getLogger().debug("--------------------------------------");
		ChatAllModel model = super.getModel(event);
		if(source == messageTextField || source == sendButton) {
			System.out.println("Send Message Controller");
			model.setMessage(messageTextField.getText());
			model.setReceiver(receiverName.getText());
			model.setActionType(ChatActionType.SEND_MESSAGE);
			messageTextField.setText("");}
		else if(source == searchUserTextfield) {
			model.setUserName(searchUserTextfield.getText());
			model.setActionType(ChatActionType.SEARCH_ONLINE_USER);
			searchUserTextfield.setText("");}
		else if(source == userOnlineList) {
			model.setReceiver(userOnlineList.getSelectionModel().getSelectedItem().getUserName());
			model.setActionType(ChatActionType.SELECT_RECEIVER);
			getLogger().debug("This is Controller {}",model.getActionType());
		}
		return model;
	}

	@Override
	protected ChatEventType getEventType(Event event) {
		return ChatEventType.CHAT;
	}

}

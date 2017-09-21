/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tvd12.ezyfoxserver.chat.client.view;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatCommands;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatEventType;
import com.tvd12.ezyfoxserver.chat.client.constant.ChatViewPath;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatAllController;
import com.tvd12.ezyfoxserver.chat.client.controller.ChatController;
import com.tvd12.ezyfoxserver.chat.client.data.ChatMessage;
import com.tvd12.ezyfoxserver.chat.client.data.ChatUser;
import com.tvd12.ezyfoxserver.chat.client.model.ChatModel;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author MyPC
 */
public class ChatAllView extends ChatAbstractView {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void beforeShow() {
		getView(ChatEventType.LOGIN, ChatModel.SUCCESS).hide();
	}

	@Override
	public void update(Object cmd, Object data) {
		if (cmd.equals(ChatCommands.SEARCH_ONLINE_USER)) {
			logger.debug("Debug SEARCH_USER_ONLINE ChatALLVIEW {}",data.toString());
			ChatUser user = (ChatUser) data;
			ListView<ChatUser> listUser = getListUserOnline();
			listUser.getItems().add(user);
		} else if (cmd.equals(ChatCommands.SELECT_RECEIVER)) {
			logger.debug("Debug Select_Receiver ChatALLVIEW {}",data.toString());
			ChatUser userReceiver = (ChatUser) data;
			Label userReceiverLabel = getLabelUser("receiverName");
			userReceiverLabel.setText(userReceiver.getUserName());
		} else {
			System.out.println("ChatView");
			ChatMessage chatMessage = (ChatMessage) data;
			if (!chatMessage.getMessage().equals("")) {
				ListView<ChatMessage> listView = getMessageListView();
				listView.getItems().add((ChatMessage) data);
			} else {

			}
		}
	}

	private ListView<ChatMessage> getMessageListView() {
		return getViewItem("messageList");
	}

	private ListView<ChatUser> getListUserOnline() {
		return getViewItem("userOnlineList");
	}
	private Label getLabelUser(String LabelUserReceiver) {
		return getViewItem(LabelUserReceiver);
	}

	@Override
	protected ChatController getController() {
		return new ChatAllController();
	}

	@Override
	protected String getViewPath() {
		return ChatViewPath.CHAT_ALL;
	}

}

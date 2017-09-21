package com.tvd12.ezyfoxserver.chat.client.model;

import com.tvd12.ezyfoxserver.chat.client.constant.ChatActionType;
import com.tvd12.ezyfoxserver.entity.EzyArray;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatAllModel extends ChatAbstractModel {

	private String message;
	private String userName;
	private String receiver;
	private ChatActionType actionType;

	@Override
	public String execute() {
		switch (actionType) {
		case SEND_MESSAGE:
			System.out.println("Send Message Model");
			sendChatMessageRequest();
			return SUCCESS;
		case SEARCH_ONLINE_USER:
			getLogger().debug("This is ChatAll Model Case Search USer");
			searchOnlineUser();
			return SUCCESS;
		case SELECT_RECEIVER:
			getLogger().debug("This is ChatAll Model Case SelectReceiver");
			selectReceiver();
			return SUCCESS;
		default:
			return SUCCESS;
		}
		
	}
	
	
	protected void searchOnlineUser() {
		EzyArray params = newArrayBuilder()
				.append(userName)
				.build();
		getAppContext().sendRequest("search_online_user", params);
	}
	protected void selectReceiver() {
		EzyArray params = newArrayBuilder()
				.append(receiver)
				.build();
		getAppContext().sendRequest("select_receiver", params);
		getLogger().debug("This is Chat Model {}",(String)params.get(0));
	} 

	protected void sendChatMessageRequest() {
		EzyArray params = newArrayBuilder()
				.append(message)
				.append(receiver)
				.build();
			getAppContext().sendRequest("2", params);
	}
}

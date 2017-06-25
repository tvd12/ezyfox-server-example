package com.tvd12.ezyfoxserver.chat.client.model;

import com.tvd12.ezyfoxserver.entity.EzyArray;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatAllModel extends ChatAbstractModel {

	private String message;

	@Override
	public String execute() {
		sendChatMessageRequest();
		return SUCCESS;
	}

	protected void sendChatMessageRequest() {
		EzyArray params = newArrayBuilder().append(message).build();
		getAppContext().sendRequest("1", params);
	}
}

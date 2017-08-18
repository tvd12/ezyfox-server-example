package com.tvd12.ezyfoxserver.chat.client.model;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.client.cmd.EzySendRequest;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.entity.EzyData;
import com.tvd12.ezyfoxserver.util.EzyEntityBuilders;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatLoginModel extends EzyEntityBuilders implements ChatModel {

	private String username;
	private String password;
	
	@Override
	public String execute() {
		EzyClientContext context = getClientContext();
		context.get(EzySendRequest.class)
			.sender(context.getMe())
			.request(newLoginRequest())
			.execute();
		return SUCCESS;
	}
	
	protected EzyClientContext getClientContext() {
		return ChatSingleton.getInstance().getClientContext();
	}

	protected EzyLoginRequest newLoginRequest() {
		return EzyLoginRequest.builder()
				.username(getUsername())
				.password(getPassword())
				.data(newLoginInData())
				.build();
	}
	
	protected EzyData newLoginInData() {
		return newArrayBuilder()
				.append("1.0.0")
				.append("android")
				.build();
	}
}

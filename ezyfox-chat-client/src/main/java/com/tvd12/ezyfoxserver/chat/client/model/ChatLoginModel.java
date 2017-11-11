package com.tvd12.ezyfoxserver.chat.client.model;

import com.tvd12.ezyfoxserver.chat.client.ChatSingleton;
import com.tvd12.ezyfoxserver.client.cmd.EzySendRequest;
import com.tvd12.ezyfoxserver.client.context.EzyClientContext;
import com.tvd12.ezyfoxserver.client.request.EzyLoginRequest;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyData;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatLoginModel extends ChatAbstractModel {

	private String username;
	private String password;
	
	@Override
	public String execute() {
		System.out.println("Chat Login Model");
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
	protected void LoginAndSaveUser() {
		EzyArray params = newArrayBuilder()
				.append(username)
				.append(password)
				.build();
		getClientContext().sendPluginRequest("SaveUser", params);
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

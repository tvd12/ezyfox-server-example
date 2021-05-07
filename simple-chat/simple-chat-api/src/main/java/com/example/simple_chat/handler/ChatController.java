package com.example.simple_chat.handler;

import java.util.Set;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@EzyRequestController("chat")
public class ChatController {

	@EzyAutoBind
	private EzyResponseFactory appResponseFactory; 
	
	@EzyDoHandle("sendMessageToOne")
	public void sendMessageToOne(EzyUser user, SendMessageToOneRequest request) {
		appResponseFactory.newObjectResponse()
			.command("chat/sendMessage")
			.data(new SendMessageResponse(user.getName(), request.getMessage()))
			.username(request.getTo()) // send response to which user has that username
			.execute(); // execute to send response
			
	}
	
	@EzyDoHandle("sendMessageToGroup")
	public void sendMessageToGroup(EzyUser user, SendMessageToOneRequest request) {
		appResponseFactory.newObjectResponse()
			.command("chat/sendMessage")
			.data(new SendMessageResponse(user.getName(), request.getMessage()))
			.usernames(request.getTo()) // send response to a collection of users
			.execute(); // execute to send response
			
	}
	
	@Data
	@EzyObjectBinding(write = false)
	public static class SendMessageToOneRequest {
		private String to;
		private String message;
	}
	
	@Getter
	@EzyObjectBinding(read = false)
	@AllArgsConstructor
	public static class SendMessageResponse {
		private String from;
		private String message;
	}
	
	@Data
	@EzyObjectBinding(write = false)
	public static class SendMessageToGroupRequest {
		private Set<String> to;
		private String message;
	}
		
}

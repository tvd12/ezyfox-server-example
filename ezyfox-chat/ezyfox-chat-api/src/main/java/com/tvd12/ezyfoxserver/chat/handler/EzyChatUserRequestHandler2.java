package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzyPrototype;
import com.tvd12.ezyfoxserver.binding.EzyDataBinding;
import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfoxserver.chat.component.EzyResponseFactory;
import com.tvd12.ezyfoxserver.chat.data.EzyChatMessage;

import lombok.Setter;

@EzyPrototype(properties = {
		@EzyKeyValue(key = "type", value = "REQUEST_HANDLER"),
		@EzyKeyValue(key = "cmd", value = "2")
})
@EzyArrayBinding(indexes = {"message", "receiver"})
@Setter
public class EzyChatUserRequestHandler2 
		extends EzyClientRequestHandler
		implements EzyDataBinding {
	
	private String message;
	private String receiver;
	
	@EzyAutoBind
	private EzyResponseFactory responseFactory;

	@Override
	public void handle() {
		getLogger().info("user {} chat {}", user.getName(), message);
		response(newChatMessage());
	}
	
	private EzyChatMessage newChatMessage() {
		EzyChatMessage object = new EzyChatMessage();
		object.setMessage(message);
		object.setReceiver(receiver);
		object.setSender(user.getName());
		return object;
	}
	
	private void response(EzyChatMessage cmessage) {
		responseFactory.newObjectResponse()
			.command("2")
			.data(cmessage)
			.user(user)
			.username(receiver)
			.execute();
	}

}

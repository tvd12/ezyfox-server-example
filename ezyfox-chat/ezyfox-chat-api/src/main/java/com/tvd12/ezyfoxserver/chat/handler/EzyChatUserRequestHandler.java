package com.tvd12.ezyfoxserver.chat.handler;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfoxserver.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzyPrototype;
import com.tvd12.ezyfoxserver.binding.EzyDataBinding;
import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfoxserver.chat.component.EzyResponseFactory;
import com.tvd12.ezyfoxserver.chat.data.EzyChatMessage;
import com.tvd12.ezyfoxserver.chat.repo.EzyChatMessageRepo;

import lombok.Setter;

@EzyPrototype(properties = { @EzyKeyValue(key = "type", value = "REQUEST_HANDLER"),
		@EzyKeyValue(key = "cmd", value = "2") })
@EzyArrayBinding(indexes = { "message", "receiver" })
@Setter
public class EzyChatUserRequestHandler extends EzyClientRequestHandler implements EzyDataBinding {

	private String message;
	private String receiver;

	@EzyAutoBind
	private EzyChatMessageRepo ezyChatMessageRepo;

	@EzyAutoBind
	private EzyResponseFactory responseFactory;

	@Override
	public void handle() {
		getLogger().info("user {} chat with message {}", user.getName(), message);
		EzyChatMessage message = newChatMessage(0);
		response(message);
		if (!message.getMessage().equals("")) {
			getLogger().debug("Message? {}", message.getMessage());
			ezyChatMessageRepo.save(message);
		}
		List<EzyChatMessage> messages = new ArrayList<>();
		for(int i = 0 ; i < 300 ; i++)
			messages.add(newChatMessage(i));
		List<Thread> threads = new ArrayList<>();
		for(EzyChatMessage m : messages)
			threads.add(new Thread(() -> response(m)));
		for(Thread thread : threads)
			thread.start();
	}

	private EzyChatMessage newChatMessage(int index) {
		EzyChatMessage object = new EzyChatMessage();
		object.setMessage(message + "#" + index);
		object.setReceiver(receiver);
		object.setSender(user.getName());
		return object;
	}

	private void response(EzyChatMessage cmessage) {
		responseFactory.newArrayResponse()
			.command("2")
			.data(cmessage)
			.user(user)
			.username(receiver)
			.execute();
	}

}

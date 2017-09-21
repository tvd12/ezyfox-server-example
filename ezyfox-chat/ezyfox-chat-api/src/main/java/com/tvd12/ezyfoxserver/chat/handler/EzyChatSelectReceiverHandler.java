package com.tvd12.ezyfoxserver.chat.handler;

import static com.tvd12.ezyfoxserver.chat.constant.EzyChatCommands.SELECT_RECEIVER;

import com.tvd12.ezyfoxserver.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzyPrototype;
import com.tvd12.ezyfoxserver.binding.EzyDataBinding;
import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfoxserver.chat.component.EzyResponseFactory;
import com.tvd12.ezyfoxserver.chat.data.EzyChatUser;

import lombok.Setter;

@EzyPrototype(properties = { @EzyKeyValue(key = "type", value = "REQUEST_HANDLER"),
@EzyKeyValue(key = "cmd", value = SELECT_RECEIVER) })
@EzyArrayBinding(indexes = { "receiver"})
@Setter
public class EzyChatSelectReceiverHandler extends EzyClientRequestHandler implements EzyDataBinding {


	private String receiver;
	@EzyAutoBind
	private EzyResponseFactory responseFactory;
	@Override
	public void handle() {
		getLogger().debug("Select_Receiver {}", receiver);
		EzyChatUser userReceiver = newUserReceiver(receiver);
		reponse(userReceiver);
	}
	public EzyChatUser newUserReceiver(String receiverName) {
		EzyChatUser userReceiver = new EzyChatUser();
		userReceiver.setUserName(receiverName);
		return userReceiver;
	}
	public void reponse(EzyChatUser userRecerver) {
		responseFactory
		.newArrayResponse()
		.command(SELECT_RECEIVER)
		.data(userRecerver)
		.user(user)
		.execute();
	}

}

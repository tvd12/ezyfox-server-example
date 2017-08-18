package com.tvd12.ezyfoxserver.chat.handler;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzyPrototype;
import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.wrapper.EzyUserManager;

import lombok.Setter;

@EzyPrototype(properties = {
		@EzyKeyValue(key = "type", value = "REQUEST_HANDLER"),
		@EzyKeyValue(key = "cmd", value = "3")
})
public class EzyChatListOnlineUserHandler extends EzyClientRequestHandler {

	@EzyAutoBind
	@Setter
	private EzyUserManager userManager;
	
	@Override
	public void handle() {
		List<String> online = new ArrayList<>();
		for(EzyUser usr : userManager.getUserList())
			online.add(usr.getName());
		appContext.get(EzyAppResponse.class)
			.command("3")
			.user(user)
			.params(newArrayBuilder().append(online))
			.execute();
	}

}

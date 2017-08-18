package com.tvd12.ezyfoxserver.chat.controller;

import com.tvd12.ezyfoxserver.bean.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.event.EzyUserJoinedAppEvent;
import com.tvd12.ezyfoxserver.wrapper.EzyUserManager;

@EzySingleton(properties = {
		@EzyKeyValue(key = "type", value = "EVENT_HANDLER"),
		@EzyKeyValue(key = "name", value = "USER_JOINED_APP")
})
public class EzyChatUserJoinedAppController 
		extends EzyAbstractAppEventController<EzyUserJoinedAppEvent> {

	@Override
	public void handle(EzyAppContext ctx, EzyUserJoinedAppEvent event) {
		getLogger().debug("handle user joined app");
		EzyUser user = event.getUser();
		EzyUserManager userManager = ctx.getApp().getUserManager();
		ctx.get(EzyAppResponse.class)
			.command("4")
			.users(userManager.getUserList())
			.params(newArrayBuilder().append(user.getName()))
			.execute();
	}
	
}

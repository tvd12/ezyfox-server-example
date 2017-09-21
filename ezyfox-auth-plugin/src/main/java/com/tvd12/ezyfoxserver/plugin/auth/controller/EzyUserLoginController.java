package com.tvd12.ezyfoxserver.plugin.auth.controller;

import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import com.tvd12.ezyfoxserver.plugin.auth.component.EzyResponseFactory;
import com.tvd12.ezyfoxserver.plugin.auth.data.ChatUser;
import com.tvd12.ezyfoxserver.plugin.auth.repo.ChatUserRepo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzySingleton(properties = {
		@EzyKeyValue(key = "type1", value = "EVENT_HANDLER"),
		@EzyKeyValue(key = "name", value = "USER_LOGIN")
})
public class EzyUserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@EzyAutoBind
	private ChatUserRepo chatUserRepo;
	
	@EzyAutoBind
	private EzyResponseFactory responseFactory;

	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		getLogger().info("handle user {} login", event.getUsername());
		ChatUser userLogin = newChatUser(event);
		// response(userLogin);
		try {
			chatUserRepo.save(userLogin);}catch(Exception e) {
				getLogger().debug("Exeption is: {}",e.getMessage() );
			}

	}

	private ChatUser newChatUser(EzyUserLoginEvent event) {
		ChatUser user = new ChatUser();
		user.setUserName(event.getUsername());
		user.setPassWord(event.getPassword());
		user.setOnlineFlag("true");
		return user;
	}

	private void response(ChatUser userLogin) {
		chatUserRepo.save(userLogin);
		responseFactory.newArrayResponse().command("SaveUser").data(userLogin).execute();
	}

}
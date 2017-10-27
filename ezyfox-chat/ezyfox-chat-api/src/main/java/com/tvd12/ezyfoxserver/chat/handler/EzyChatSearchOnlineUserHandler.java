package com.tvd12.ezyfoxserver.chat.handler;

import static com.tvd12.ezyfoxserver.chat.constant.EzyChatCommands.SEARCH_ONLINE_USER;

import com.tvd12.ezyfoxserver.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.bean.annotation.EzyPrototype;
import com.tvd12.ezyfoxserver.binding.EzyDataBinding;
import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfoxserver.chat.component.EzyResponseFactory;
import com.tvd12.ezyfoxserver.chat.constant.EzyChatErrors;
import com.tvd12.ezyfoxserver.chat.data.EzyChatError;
import com.tvd12.ezyfoxserver.chat.data.EzyChatUser;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.wrapper.EzyUserManager;

import lombok.Setter;

@EzyPrototype(properties = { @EzyKeyValue(key = "type", value = "REQUEST_HANDLER"),
		@EzyKeyValue(key = "cmd", value = SEARCH_ONLINE_USER) })
@EzyArrayBinding(indexes = { "userName"})
@Setter
public class EzyChatSearchOnlineUserHandler extends EzyClientRequestHandler implements EzyDataBinding {

	private String userName;

	@EzyAutoBind
	private EzyUserManager userManager;

	@EzyAutoBind
	private EzyResponseFactory responseFactory;

	@Override
	public void handle() {
		getLogger().debug("This is Handler");
		if (userManager.containsUser(userName)) {
			getLogger().debug("found online user {}", userName);
			EzyChatUser user = newChatUser();
			responseFound(user);
		} else {
			getLogger().debug("USER NAME {}", userName);
			getLogger().debug("not found online user {}", userName);
			responseNotFound();
		}

	}

	private void responseFound(EzyChatUser userSearch) {
		responseFactory
			.newArrayResponse()
			.command(SEARCH_ONLINE_USER)
			.data(userSearch)
			.user(user)
			.execute();
	}
	
	private EzyChatUser newChatUser() {
		EzyChatUser userSearch = new EzyChatUser();
		EzyUser user = userManager.getUser(userName);
		userSearch.setUserName(user.getName());
		return userSearch;
	}
	
	private void responseNotFound() {
		responseFactory
			.newObjectResponse()
			.command("error")
			.data(notFoundError())
			.user(user)
			.execute();
	}

	private EzyChatError notFoundError() {
		return new EzyChatError(EzyChatErrors.SEARCH_ONLINE_USER_NOT_FOUND, "user " + userName + " hasn't logged in");
	}
}

package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfoxserver.annotation.EzyKeyValue;
import com.tvd12.ezyfoxserver.bean.annotation.EzyPrototype;
import com.tvd12.ezyfoxserver.binding.EzyDataBinding;
import com.tvd12.ezyfoxserver.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfoxserver.command.EzyAppResponse;
import com.tvd12.ezyfoxserver.entity.EzyArray;

import lombok.Setter;

@EzyPrototype(properties = {
		@EzyKeyValue(key = "type", value = "REQUEST_HANDLER"),
		@EzyKeyValue(key = "cmd", value = "1")
})
@EzyArrayBinding
public class EzyChatSystemRequestHandler 
		extends EzyClientRequestHandler
		implements EzyDataBinding {

	@Setter
	private String message;
	
	@Override
	public void handle() {
		EzyArray response = newArrayBuilder().append(message + ", too!").build();
		getLogger().info("user {} chat {}", user.getName(), message);
		appContext.get(EzyAppResponse.class)
			.command("1")
			.params(response)
			.user(user)
			.execute();
	}

}

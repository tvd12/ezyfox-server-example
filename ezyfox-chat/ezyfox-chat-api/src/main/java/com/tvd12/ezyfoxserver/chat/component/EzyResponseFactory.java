package com.tvd12.ezyfoxserver.chat.component;

import com.tvd12.ezyfoxserver.chat.command.EzyArrayResponse;
import com.tvd12.ezyfoxserver.chat.command.EzyObjectResponse;

public interface EzyResponseFactory {

	EzyArrayResponse newArrayResponse();
	
	EzyObjectResponse newObjectResponse();
	
}

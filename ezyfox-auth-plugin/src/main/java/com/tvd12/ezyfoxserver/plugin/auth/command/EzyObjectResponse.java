package com.tvd12.ezyfoxserver.plugin.auth.command;

import com.tvd12.ezyfoxserver.plugin.auth.command.EzyChatResponse;
import com.tvd12.ezyfoxserver.plugin.auth.command.EzyObjectResponse;

public interface EzyObjectResponse extends EzyChatResponse<EzyObjectResponse> {
	
	EzyObjectResponse param(Object key, Object value);
	
	
}

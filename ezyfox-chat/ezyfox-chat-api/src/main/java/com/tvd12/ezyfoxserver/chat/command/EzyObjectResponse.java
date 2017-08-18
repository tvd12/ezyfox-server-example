package com.tvd12.ezyfoxserver.chat.command;

public interface EzyObjectResponse extends EzyChatResponse<EzyObjectResponse> {
	
	EzyObjectResponse param(Object key, Object value);
	
	
}

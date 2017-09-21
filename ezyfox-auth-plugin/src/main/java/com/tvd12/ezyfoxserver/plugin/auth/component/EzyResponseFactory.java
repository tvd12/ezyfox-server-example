package com.tvd12.ezyfoxserver.plugin.auth.component;

import com.tvd12.ezyfoxserver.plugin.auth.command.EzyArrayResponse;
import com.tvd12.ezyfoxserver.plugin.auth.command.EzyObjectResponse;

public interface EzyResponseFactory {

	EzyArrayResponse newArrayResponse();
	
	EzyObjectResponse newObjectResponse();
	
}

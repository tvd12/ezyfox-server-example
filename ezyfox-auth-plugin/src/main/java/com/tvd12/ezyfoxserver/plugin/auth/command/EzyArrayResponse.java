package com.tvd12.ezyfoxserver.plugin.auth.command;

import com.tvd12.ezyfoxserver.plugin.auth.command.EzyArrayResponse;
import com.tvd12.ezyfoxserver.plugin.auth.command.EzyChatResponse;

public interface EzyArrayResponse extends EzyChatResponse<EzyArrayResponse> {

	EzyArrayResponse param(Object value);
	
}

package com.tvd12.ezyfoxserver.chat.handler;

import com.tvd12.ezyfoxserver.context.EzyContext;
import com.tvd12.ezyfoxserver.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyUser;

public interface EzyClientRequestHandler {

	void handle(EzyContext context, EzyUser user, EzyArray params);
	
}

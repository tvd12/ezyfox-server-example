package com.tvd12.ezyfoxserver.stresstest.handler;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginSuccessHandler;
import com.tvd12.ezyfoxserver.client.request.EzyAccessAppRequest;

public class LoginSuccessHandler extends EzyLoginSuccessHandler {

	@Override
	protected void handleLoginSuccess(EzyArray joinedApps, EzyData responseData) {
		client.send(new EzyAccessAppRequest("hello-world"));
	}
	
}

package com.tvd12.ezyfoxserver.stresstest.handler;

import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfoxserver.client.handler.EzyLoginSuccessHandler;
import com.tvd12.ezyfoxserver.client.request.EzyAppAccessRequest;

public class LoginSuccessHandler extends EzyLoginSuccessHandler {

	@Override
	protected void handleLoginSuccess(EzyData responseData) {
		client.send(new EzyAppAccessRequest("hello-world"));
		
//		EzyApp app = new EzySimpleApp(client.getZone(), 1, "hello-world");
//		app.send("broadcastMessage", newMessageData());
	}
	
	
//	private EzyObject newMessageData() {
//		return EzyEntityFactory.newObjectBuilder()
//			.append("message", "Message#" + 1)
//			.build();
//	}
}
